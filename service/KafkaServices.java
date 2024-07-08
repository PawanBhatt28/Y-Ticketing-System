package com.kapture.ticketservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.kapture.ticketservice.constants.Constants;
import com.kapture.ticketservice.dto.TicketDTO;

@Service
public class KafkaServices implements Constants {

	private final Logger logger = LoggerFactory.getLogger(KafkaListener.class);

	
	private KafkaTemplate<String, Object> kafkaTemplate;
	private TicketService ticketService;

	
	public KafkaServices(KafkaTemplate<String, Object> kafkaTemplate, TicketService ticketService) {
		this.kafkaTemplate = kafkaTemplate;
		this.ticketService = ticketService;
	}

	public List<TicketDTO> produceTicket(List<TicketDTO> ticketsDTO) {


		return ticketsDTO.stream().peek(ticket -> {
			CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(kafkaTopic, ticket);
			future.whenComplete((result, exception) -> {
				if (exception != null) {
					logger.info("Error in pushing the ticket in the Kafka Server ", exception);
				} else {
					logger.info("Successfully pushed ticket in the Kafka Server");
				}
			});
		}).collect(Collectors.toList());
	}

	@KafkaListener(topics = kafkaTopic, groupId = listenerGroup)
	public void consumeTicket(TicketDTO ticketDTO) {
		try {
			ticketService.saveTicket(ticketDTO);
		} catch (Exception e) {
			logger.info("Error in Kafka Listener ", e);
			return;
		} finally {
			logger.info("Listener Consumed");
		}
	}

}
