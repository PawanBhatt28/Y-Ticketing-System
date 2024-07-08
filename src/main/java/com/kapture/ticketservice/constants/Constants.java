package com.kapture.ticketservice.constants;

public interface Constants {
	String select = "FROM Ticket t";
	String update = "UPDATE Ticket t SET t.status = :status, t.title = :title, t.lastModifiedDate = :lastModifiedDate, WHERE t.ticketCode = :ticketCode AND t.clientId = :clientId";
	
	String kafkaTopic = "topic-ticket";
	String listenerGroup = "group-1";
	
	String RedisCacheKey = "Ticket::";
}
