package com.kapture.ticketservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kapture.ticketservice.dto.ResponseDTO;
import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.service.KafkaServices;
import com.kapture.ticketservice.service.TicketService;
import com.kapture.ticketservice.util.TicketMapper;
import com.kapture.ticketservice.validation.TicketValidator;

@RestController
public class TicketController {

	private TicketService ticketService;
	private TicketMapper ticketMapper;
	private TicketValidator ticketValidator;
	private KafkaServices kafkaServices;

	public TicketController(TicketService ticketService, TicketMapper ticketMapper, TicketValidator requestValidator,
			KafkaServices kafkaServices) {
		this.ticketService = ticketService;
		this.ticketMapper = ticketMapper;
		this.ticketValidator = requestValidator;
		this.kafkaServices = kafkaServices;
	}

	@GetMapping("/ticket")
	public ResponseEntity<ResponseDTO> searchTicket(@RequestBody TicketDTO ticketDTO) {
		ResponseDTO validationResponse = ticketValidator.fetchTicketValidator(ticketDTO);
		try {
			if ("Failed".equals(validationResponse.getStatus())) {
				return ResponseEntity.badRequest().body(validationResponse);
			}
			List<Ticket> tickets = ticketService.getTickets(ticketDTO);

			ResponseDTO successResponse = new ResponseDTO("Tickets found successfully", "Success", tickets, HttpStatus.OK);
			return new ResponseEntity<>(successResponse, successResponse.getHttpStatus());

		} catch (Exception e) {
			ResponseDTO errorResponse = new ResponseDTO(validationResponse.getMessage(), "Failed", ticketDTO, HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
		}
	}

	@PostMapping("/add")
	public ResponseEntity<ResponseDTO> addTicket(@RequestBody TicketDTO ticketDTO) {
		ResponseDTO validationResponse = ticketValidator.addTicketValidator(ticketDTO);
		try {
			if ("Failed".equals(validationResponse.getStatus())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationResponse);
			}
			Ticket ticket = ticketService.saveTicket(ticketDTO);
			List<Ticket> databaseResponse = List.of(ticket);

			ResponseDTO successResponse = new ResponseDTO("Ticket added successfuly", "Success", databaseResponse, HttpStatus.CREATED);
			return new ResponseEntity<>(successResponse, successResponse.getHttpStatus());

		} catch (Exception e) {
			ResponseDTO errorResponse = new ResponseDTO(validationResponse.getMessage(), "Failed", ticketDTO, HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
		}
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseDTO> updateTicket(@RequestBody TicketDTO ticketDTO) {
		ResponseDTO validationResponse = ticketValidator.updateTicketValidator(ticketDTO);
		try {
			if ("Failed".equals(validationResponse.getStatus())) {
				return ResponseEntity.badRequest().body(validationResponse);
			}
			ticketService.updateTicket((TicketDTO) validationResponse.getObject());

			ResponseDTO successResponse = new ResponseDTO("Ticket updated successfully", "Success", null, HttpStatus.OK);
			return new ResponseEntity<>(successResponse, successResponse.getHttpStatus());

		} catch (Exception e) {
			ResponseDTO errorResponse = new ResponseDTO(validationResponse.getMessage(), "Failed", ticketDTO, HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
		}
	}

}

//List<Ticket> tickets = kafkaServices.produceTicket(ticketsDTO).stream()
//		.map(ticketDTO -> ticketMapper.map(ticketDTO)).collect(Collectors.toList());
//		responseDTO.setObject(tickets);