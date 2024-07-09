package com.kapture.ticketservice.service;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.stereotype.Service;

import com.kapture.ticketservice.constants.Constants;
import com.kapture.ticketservice.dao.TicketRepository;
import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.exception.InvalidInputException;
import com.kapture.ticketservice.util.TicketMapper;

@EnableRedisRepositories
@Service
public class TicketService {

	private TicketRepository ticketRepository;
	private TicketMapper ticketmapper;

	@Autowired
	private RedissonClient redisClient;

	public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper) {
		this.ticketRepository = ticketRepository;
		this.ticketmapper = ticketMapper;
	}

	public Ticket saveTicket(TicketDTO ticketDTO) throws InvalidInputException {
		Ticket ticket = ticketmapper.map(ticketDTO);
		ticket.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
		Ticket savedObject = ticketRepository.saveTicket(ticket);
		String cacheKey = null;
		Ticket savedTicket = null;
		if (savedObject instanceof Ticket) {
			savedTicket = (Ticket) savedObject;
			cacheKey = Constants.RedisCacheKey + savedTicket.getClientId() + "->" + savedTicket.getTicket_code();
			RBucket<Ticket> rBucket = redisClient.getBucket(cacheKey);
			rBucket.set(savedTicket, Duration.ofMinutes(10));
			return savedTicket;
		}
		return savedObject;

	}

	public Ticket getTicket(int clientId, int ticketCode) {
		Ticket ticket = null;
		String cacheKey = Constants.RedisCacheKey + clientId + "->" + ticketCode;

		RBucket<Ticket> rBucket = redisClient.getBucket(cacheKey);
		ticket = rBucket.get();
		if (ticket == null)
			ticket = ticketRepository.getTicketByIndex(clientId, ticketCode);

		return ticket;
	}

	public List<Ticket> getTickets(TicketDTO ticketDTO) {
		List<Ticket> tickets = ticketRepository.getTicket(ticketDTO);
		return tickets;
	}

	public Ticket updateTicket(TicketDTO ticketDTO) throws InvalidInputException {
		Ticket updatedTicket = ticketRepository.updateTicket(ticketDTO);
		// Bug fixed as per the instruction.
		String cacheKey = null;
		if (updatedTicket != null) {
			cacheKey = Constants.RedisCacheKey + ticketDTO.getClientId() + "->" + ticketDTO.getTicketCode();
			RBucket<Ticket> rBucket = redisClient.getBucket(cacheKey);
			rBucket.set(updatedTicket, Duration.ofMinutes(10));
		}
		return updatedTicket;
	}
}