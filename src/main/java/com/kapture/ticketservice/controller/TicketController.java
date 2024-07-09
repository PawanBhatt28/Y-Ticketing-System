package com.kapture.ticketservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kapture.ticketservice.dto.ResponseDTO;
import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.service.KafkaServices;
import com.kapture.ticketservice.service.TicketService;
import com.kapture.ticketservice.util.InvalidInputException;
import com.kapture.ticketservice.validation.RequestValidator;

@RestController
public class TicketController {

	private TicketService ticketService;
	private RequestValidator requestValidator;
	private KafkaServices kafkaServices;

	public TicketController(TicketService ticketService, RequestValidator requestValidator,
			KafkaServices kafkaServices) {
		this.ticketService = ticketService;
		this.requestValidator = requestValidator;
		this.kafkaServices = kafkaServices;
	}

	@PostMapping("ticket")
	public ResponseEntity<ResponseDTO> ResposeDTO(@RequestBody TicketDTO ticketDTO) throws InvalidInputException {
		ResponseDTO responseDTO = requestValidator.postRequestValidator(ticketDTO);
		try {
			Object ticket = null;
			if (responseDTO.getStatus().equals("Success")) {
				ticket = ticketService.saveTicket(ticketDTO);
				responseDTO.setObject(ticket);
			}
			if (!(ticket instanceof Ticket)) {
				responseDTO.setHttpStatus(HttpStatus.CONFLICT);
				responseDTO.setMessage("Not get saved");
				responseDTO.setStatus("Failure");
			}
		} catch (Exception e) {
			responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseDTO.setMessage("Invalid data: " + e.getMessage());
			responseDTO.setStatus("Failure");
			responseDTO.setObject(ticketDTO);
		}
		return new ResponseEntity<>(responseDTO, responseDTO.getHttpStatus());
	}

	@PostMapping("tickets")
	public ResponseEntity<ResponseDTO> saveTickets(@RequestBody List<TicketDTO> ticketsDTO)
			throws InvalidInputException {
		ResponseDTO responseDTO = new ResponseDTO();
		List<TicketDTO> invalidTickets = new ArrayList<TicketDTO>();
		for (TicketDTO ticketDTO : ticketsDTO) {
			try {
				responseDTO = requestValidator.postRequestValidator(ticketDTO);
				if (responseDTO.getStatus().equals("Failure")) {
					invalidTickets.add(ticketDTO);
					continue;
				}
				kafkaServices.produceTicket(ticketDTO);
			} catch (Exception e) {
				invalidTickets.add(ticketDTO);
				responseDTO.setMessage(e.getMessage());
			}
		}
		if (!invalidTickets.isEmpty())
			responseDTO.setObject(invalidTickets);
		return new ResponseEntity<>(responseDTO, responseDTO.getHttpStatus());

	}

	@GetMapping("ticket/{clientId}/{ticketCode}")
	public ResponseEntity<ResponseDTO> getTicket(@PathVariable int clientId, @PathVariable int ticketCode)
			throws InvalidInputException {
		ResponseDTO responseDTO = requestValidator.IndexRequestValidator(clientId, ticketCode);

		if (responseDTO.getStatus().equals("Success")) {
			responseDTO.setObject(ticketService.getTicket(clientId, ticketCode));
		}
		if (responseDTO.getObject() == null) {
			responseDTO.setHttpStatus(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(responseDTO, responseDTO.getHttpStatus());
	}

	@GetMapping("getRequiredticktes")
	public ResponseEntity<ResponseDTO> getTickets(@RequestBody TicketDTO ticketDTO) throws InvalidInputException {
		ResponseDTO responseDTO = null;
		try {
			responseDTO = requestValidator.getRequiredValidator(ticketDTO);
			if (responseDTO.getStatus().equals("Success")) {
				responseDTO.setObject(ticketService.getTickets(ticketDTO));
			}
			if (responseDTO.getObject() == null) {
				responseDTO.setMessage("No ticket Found");
			}
		} catch (Exception e) {
			responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseDTO.setMessage("Invalid data: " + e.getMessage());
			responseDTO.setStatus("Failure");
			responseDTO.setObject(ticketDTO);
		}
		return new ResponseEntity<>(responseDTO, responseDTO.getHttpStatus());
	}

	@PutMapping("update")
	public ResponseEntity<ResponseDTO> updateTicket(@RequestBody TicketDTO ticketDTO) throws InvalidInputException {
		ResponseDTO responseDTO = requestValidator.updateRequestValidator(ticketDTO);
		if (responseDTO.getStatus().equals("Success")) {
			try {
				Ticket updatedTicket = ticketService.updateTicket(ticketDTO);
				if (updatedTicket != null)
					responseDTO.setObject(updatedTicket);
				else {
					responseDTO.setHttpStatus(HttpStatus.NO_CONTENT);
				}
			} catch (Exception e) {
				responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
				responseDTO.setMessage("Invalid data: " + e.getMessage());
				responseDTO.setStatus("Failure");
				responseDTO.setObject(ticketDTO);
			}
		}
		return new ResponseEntity<>(responseDTO, responseDTO.getHttpStatus());
	}

}
