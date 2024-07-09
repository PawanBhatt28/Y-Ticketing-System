package com.kapture.ticketservice.dao;

import java.util.List;

import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.util.InvalidDataException;

public interface TicketRepository {
	public Ticket saveTicket(Ticket ticket) throws InvalidDataException;
	public List<Ticket> getTicket(TicketDTO ticketDTO);
	public Ticket getTicketByIndex(int clientId, int ticketCode);
	public Ticket updateTicket(TicketDTO ticketDTO) throws InvalidDataException;
}
