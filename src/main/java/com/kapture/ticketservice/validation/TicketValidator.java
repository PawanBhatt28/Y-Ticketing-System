package com.kapture.ticketservice.validation;

import com.kapture.ticketservice.dto.ResponseDTO;
import com.kapture.ticketservice.dto.TicketDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TicketValidator {

    Logger logger = LoggerFactory.getLogger(TicketValidator.class);

    private static final String INTEGER_PATTERN = "\\d+";
    private static final String CLIENT_ID_ERROR = "ClientId should be non-null and an integer.";
    private static final String TICKET_CODE_ERROR = "TicketCode should be non-null and an integer.";
    private static final String STATUS_TITLE_ERROR = "Status (max 20 chars) or Title (max 255 chars) is required.";
    private static final int MAX_STATUS_LENGTH = 20;
    private static final int MAX_TITLE_LENGTH = 255;

    public ResponseDTO fetchTicketValidator(TicketDTO ticketDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("Failed");
        responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);

        String clientId = ticketDTO.getClientId();
        String ticketCode = ticketDTO.getTicketCode();
        Date startDate = ticketDTO.getStartDate();
        Date endDate = ticketDTO.getEndDate();

        if (!validClientIdOrTicketCode(clientId)) {
            responseDTO.setMessage(CLIENT_ID_ERROR);
        } else if (!validClientIdOrTicketCode(ticketCode)) {
            responseDTO.setMessage(TICKET_CODE_ERROR);
        } else if (startDate == null && endDate != null) {
            responseDTO.setMessage("Require at least start date to fetch in a range");
        }
        return responseDTO;
    }

    public ResponseDTO addTicketValidator(TicketDTO ticketDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("Failed");
        responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);

        String clientId = ticketDTO.getClientId();
        String ticketCode = ticketDTO.getTicketCode();
        String ticketStatus = ticketDTO.getStatus();
        String ticketTitle = ticketDTO.getTitle();

        if (!validClientIdOrTicketCode(clientId)) {
            responseDTO.setMessage(CLIENT_ID_ERROR);
        } else if (!validClientIdOrTicketCode(ticketCode)) {
            responseDTO.setMessage(TICKET_CODE_ERROR);
        } else if (!validStatusOrTitle(ticketStatus, null)) {
            responseDTO.setMessage(STATUS_TITLE_ERROR);
        } else if (ticketTitle != null && !validStatusOrTitle(null, ticketTitle)) {
            responseDTO.setMessage(STATUS_TITLE_ERROR);
        } else {
            responseDTO.setStatus("Success");
        }
        return responseDTO;
    }

    public ResponseDTO updateTicketValidator(TicketDTO ticketDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("Failed");
        responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);

        String clientId = ticketDTO.getClientId();
        String ticketCode = ticketDTO.getTicketCode();
        String ticketStatus = ticketDTO.getStatus();
        String ticketTitle = ticketDTO.getTitle();

        if (!validClientIdOrTicketCode(clientId)) {
            responseDTO.setMessage(CLIENT_ID_ERROR);
        } else if (!validClientIdOrTicketCode(ticketCode)) {
            responseDTO.setMessage(TICKET_CODE_ERROR);
        } else if (ticketStatus == null && (ticketTitle == null || "general-ticket".equals(ticketTitle) || ticketTitle.length() < 256)) {
            responseDTO.setMessage(STATUS_TITLE_ERROR);
        } else {
            responseDTO.setStatus("Success");
        }
        return responseDTO;
    }

    private boolean validClientIdOrTicketCode(String id) {
        return id != null && (id.trim().matches(INTEGER_PATTERN) && Integer.parseInt(id) > 0);
    }

    private boolean validStatusOrTitle(String status, String title) {
        return (status != null && status.length() < 20) || (title != null && title.length() < 256);
    }
}
