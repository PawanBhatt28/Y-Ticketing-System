package com.kapture.ticketservice.validation;

import com.kapture.ticketservice.constants.Constants;
import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.exception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TicketValidator {

    Logger logger = LoggerFactory.getLogger(TicketValidator.class);

    public boolean fetchTicketValidator(TicketDTO ticketDTO) throws InvalidInputException {
        logger.info("Validating TicketDTO for fetching ticket validation");

        String clientId = ticketDTO.getClientId();
        String ticketCode = ticketDTO.getTicketCode();
        Date startDate = ticketDTO.getStartDate();
        Date endDate = ticketDTO.getEndDate();

        if (invalidClientIdOrTicketCode(clientId)) {
            throw new InvalidInputException(Constants.CLIENT_ID_ERROR);
        } else if (invalidClientIdOrTicketCode(ticketCode)) {
            throw new InvalidInputException(Constants.TICKET_CODE_ERROR);
        } else if (startDate == null && endDate != null) {
            throw new InvalidInputException("Require at least start date to fetch in a range");
        }

        logger.info("TicketDTO passed fetch ticket validation");
        return true;
    }

    public boolean addTicketValidator(TicketDTO ticketDTO) throws Exception {
        logger.info("Validating TicketDTO for adding ticket");

        String clientId = ticketDTO.getClientId();
        String ticketCode = ticketDTO.getTicketCode();
        String ticketStatus = ticketDTO.getStatus();
        String ticketTitle = ticketDTO.getTitle();

        if (invalidClientIdOrTicketCode(clientId)) {
            throw new InvalidInputException(Constants.CLIENT_ID_ERROR);
        } else if (invalidClientIdOrTicketCode(ticketCode)) {
            throw new InvalidInputException(Constants.TICKET_CODE_ERROR);
        } else if (invalidStatusOrTitle(ticketStatus, null)) {
            throw new InvalidInputException(Constants.STATUS_TITLE_ERROR);
        } else if (ticketTitle != null && invalidStatusOrTitle(null, ticketTitle)) {
            throw new InvalidInputException(Constants.STATUS_TITLE_ERROR);
        }
        logger.info("TicketDTO passed add ticket validation");
        return true;
    }

    public boolean updateTicketValidator(TicketDTO ticketDTO) throws InvalidInputException {
        logger.info("Validating TicketDTO for updating ticket");

        String clientId = ticketDTO.getClientId();
        String ticketCode = ticketDTO.getTicketCode();
        String ticketStatus = ticketDTO.getStatus();
        String ticketTitle = ticketDTO.getTitle();

        if (invalidClientIdOrTicketCode(clientId)) {
            throw new InvalidInputException(Constants.CLIENT_ID_ERROR);
        } else if (invalidClientIdOrTicketCode(ticketCode)) {
            throw new InvalidInputException(Constants.TICKET_CODE_ERROR);
        } else if (ticketStatus == null && (ticketTitle == null || "general-ticket".equals(ticketTitle) || ticketTitle.length() < 256)) {
            throw new InvalidInputException(Constants.STATUS_TITLE_ERROR);
        }
        logger.info("TicketDTO passed update ticket validation");
        return true;
    }

    private boolean invalidClientIdOrTicketCode(String id) {
        return !(id != null && (id.trim().matches(Constants.INTEGER_PATTERN) && Integer.parseInt(id) > 0));
    }

    private boolean invalidStatusOrTitle(String status, String title) {
        return !((status != null && status.length() < Constants.MAX_STATUS_LENGTH) || (title != null && title.length() < Constants.MAX_TITLE_LENGTH));
    }
}
