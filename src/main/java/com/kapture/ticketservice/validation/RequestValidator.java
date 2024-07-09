package com.kapture.ticketservice.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.kapture.ticketservice.dto.ResponseDTO;
import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.util.InvalidInputException;

@Component
public class RequestValidator {

	Logger logger = LoggerFactory.getLogger(RequestValidator.class);

	@SuppressWarnings("unused")
	public ResponseDTO postRequestValidator(TicketDTO ticketDTO) throws InvalidInputException {
		ResponseDTO responseDTO = new ResponseDTO();
		int clientId = -1;
		String title = null;
		String status = null;
		int ticketCode = -1;
		try {
			clientId = ticketDTO.getClientId();
			title = ticketDTO.getTitle();
			status = ticketDTO.getStatus();
			ticketCode = ticketDTO.getTicketCode();
			responseDTO.setHttpStatus(HttpStatus.CREATED);
			responseDTO.setMessage("Ticket is created");
			responseDTO.setStatus("Success");
			return responseDTO;
		} catch (Exception e) {
			responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseDTO.setMessage("Ticket is not created");
			responseDTO.setStatus("Failure");
			logger.info("Invalid Input");
			throw new InvalidInputException("Invalid ticket present !!!");
		}
	}

	@SuppressWarnings("unused")
	public ResponseDTO IndexRequestValidator(int reqClientId, int reqTicketCode) throws InvalidInputException {
		int clientId = 0;
		int ticketCode = 0;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			clientId = reqClientId;
			ticketCode = reqTicketCode;
			responseDTO.setHttpStatus(HttpStatus.FOUND);
			responseDTO.setMessage("Ticket may exist");
			responseDTO.setStatus("Success");
			return responseDTO;
		} catch (Exception e) {
			logger.info("Invalid Input");
			responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseDTO.setMessage("Not a valid Input");
			responseDTO.setStatus("Failure");
			throw new InvalidInputException("Not a valid input!!!");
		}
	}

	@SuppressWarnings("unused")
	public ResponseDTO getRequiredValidator(TicketDTO ticketDTO) throws InvalidInputException {
		ResponseDTO responseDTO = new ResponseDTO();
		int clientId = -1;
		String title = null;
		String status = null;
		Integer ticketCode = null;
		Integer limit = null;
		try {
			clientId = ticketDTO.getClientId();
			title = ticketDTO.getTitle();
			status = ticketDTO.getStatus();
			ticketCode = ticketDTO.getTicketCode();
			limit = ticketDTO.getLimit();
			responseDTO.setHttpStatus(HttpStatus.FOUND);
			responseDTO.setMessage("Tickets are Retrived");
			responseDTO.setStatus("Success");
			return responseDTO;
		} catch (Exception e) {
			logger.info("Invalid Input");
			responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseDTO.setMessage("Ticket is not retrived");
			responseDTO.setStatus("Failure");
			throw new InvalidInputException("Not a valid input!!!");
		}
	}


	@SuppressWarnings("unused")
	public ResponseDTO updateRequestValidator(TicketDTO ticketDTO) throws InvalidInputException {
		ResponseDTO responseDTO = new ResponseDTO();
		int clientId = -1;
		String title = null;
		String status = null;
		int ticketCode = -1;
		responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
		responseDTO.setMessage("Ticket is not Updated");
		responseDTO.setStatus("Failure");
		try {
			clientId = ticketDTO.getClientId();
			title = ticketDTO.getTitle();
			status = ticketDTO.getStatus();
			ticketCode = ticketDTO.getTicketCode();
			if ((status == null && title == null))
				return responseDTO;
			responseDTO.setHttpStatus(HttpStatus.ACCEPTED);
			responseDTO.setMessage("Ticket is Updated");
			responseDTO.setStatus("Success");
			return responseDTO;
		} catch (Exception e) {
			logger.info("Invalid Input");
			throw new InvalidInputException("Not a valid input!!!");
		}
	}
}
