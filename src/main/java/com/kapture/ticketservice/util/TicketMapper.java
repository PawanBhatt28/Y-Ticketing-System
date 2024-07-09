package com.kapture.ticketservice.util;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;

import static java.lang.Integer.valueOf;

@Component
public class TicketMapper {
	public Ticket map(TicketDTO ticketDTO) {
		Ticket ticket = new Ticket();
		ticket.setClientId(valueOf(ticketDTO.getClientId().trim()));
		ticket.setStatus(ticketDTO.getStatus());
		ticket.setTicket_code(valueOf(ticketDTO.getTicketCode().trim()));
		ticket.setTitle(ticketDTO.getTitle());
		ticket.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
		return ticket;
	}
}
