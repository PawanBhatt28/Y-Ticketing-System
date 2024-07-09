package com.kapture.ticketservice.validation;

import com.kapture.ticketservice.dto.ResponseDTO;
import com.kapture.ticketservice.dto.TicketDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.Integer.valueOf;
import static org.apache.tomcat.util.http.parser.HttpParser.isNumeric;

@Component
public class TicketValidator {

	Logger logger = LoggerFactory.getLogger(TicketValidator.class);

	public ResponseDTO fetchTicketValidator(TicketDTO ticketDTO){
		ResponseDTO responseDTO = new ResponseDTO();

		String clientId = ticketDTO.getClientId();
		Date startDate = ticketDTO.getStartDate();
		Date endDate = ticketDTO.getEndDate();

		if( clientId == null){
			responseDTO.setMessage("ClientId should be not-null and integer.");
			responseDTO.setStatus("Failed");
		}
		else{
			if(startDate == null && endDate != null){
				responseDTO.setMessage("Require at least start date to fetch in a range");
				responseDTO.setStatus("Failed");
			}
		}
		return responseDTO;
	}

	public ResponseDTO addTicketValidator(TicketDTO ticketDTO){
		ResponseDTO responseDTO = new ResponseDTO();

		String clientId = ticketDTO.getClientId();
		String ticketCode = ticketDTO.getTicketCode();
		String ticketStatus = ticketDTO.getStatus();

		if(clientId == null || !(clientId.trim().matches("\\d+"))){
			responseDTO.setMessage("ClientId should be not-null and integer.");
			responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseDTO.setStatus("Failed");
		}
		else if(ticketCode == null || !(ticketCode.trim().matches("\\d+"))){
			responseDTO.setMessage("TicketCode should be not-null and integer.");
			responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseDTO.setStatus("Failed");
		}else if(ticketStatus == null ){
			responseDTO.setMessage("Ticket status is mandatory, it cannot be null");
			responseDTO.setStatus("Failed");
		}else{
			responseDTO.setStatus("Success");
		}
		return responseDTO;
	}

	public ResponseDTO updateTicketValidator(TicketDTO ticketDTO){
		ResponseDTO responseDTO = new ResponseDTO();

		String clientId = ticketDTO.getClientId();
		String ticketCode = ticketDTO.getTicketCode();
		String ticketStatus = ticketDTO.getStatus();
		String ticketTitle = ticketDTO.getTitle();

		if(clientId == null || !(clientId.trim().matches("\\d+"))){
			responseDTO.setMessage("ClientId should be not-null and integer.");
			responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseDTO.setStatus("Failed");
		}
		else if(ticketCode == null || !(ticketCode.trim().matches("\\d+"))){
			responseDTO.setMessage("TicketCode should be not-null and integer.");
			responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseDTO.setStatus("Failed");
		}else if (ticketStatus == null && (ticketTitle == null || "general-ticket".equals(ticketTitle))){
			responseDTO.setMessage("Either status or title is mandatory");
			responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseDTO.setStatus("Failed");
		}else{
			responseDTO.setStatus("Success");
		}
		return responseDTO;
	}
}
