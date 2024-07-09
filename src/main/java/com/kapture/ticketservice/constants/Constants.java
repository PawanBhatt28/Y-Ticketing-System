package com.kapture.ticketservice.constants;

public class Constants {
    public static final String select = "FROM Ticket t";
    public static final String update = "UPDATE Ticket t SET t.status = :status, t.title = :title, t.lastModifiedDate = :lastModifiedDate, WHERE t.ticketCode = :ticketCode AND t.clientId = :clientId";

    public static final String kafkaTopic = "topic-ticket";
    public static final String listenerGroup = "group-1";

    public static final String RedisCacheKey = "Ticket::";

    public static final String INTEGER_PATTERN = "\\d+";

    public static String CLIENT_ID_ERROR = "ClientId should be non-null and an integer.";
    public static final String TICKET_CODE_ERROR = "TicketCode should be non-null and an integer.";
    public static final String STATUS_TITLE_ERROR = "Status (max 20 chars) or Title (max 255 chars) is required.";

    public static final int MAX_STATUS_LENGTH = 20;
    public static final int MAX_TITLE_LENGTH = 255;
}
