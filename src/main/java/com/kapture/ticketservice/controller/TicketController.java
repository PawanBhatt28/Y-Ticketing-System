package com.kapture.ticketservice.controller;

import java.util.List;

import com.kapture.ticketservice.exception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kapture.ticketservice.dto.ResponseDTO;
import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.service.TicketService;
import com.kapture.ticketservice.validation.TicketValidator;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    TicketService ticketService;
    @Autowired
    TicketValidator ticketValidator;

    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> searchTicket(@RequestBody TicketDTO ticketDTO) {
        logger.info("Starting search ticket execution\n TicketDTO : {}", ticketDTO);

        ResponseDTO clientResponse;
        try {
            boolean validationResponse = ticketValidator.fetchTicketValidator(ticketDTO);
            List<Ticket> tickets = ticketService.getTickets(ticketDTO);
            clientResponse = new ResponseDTO("Tickets found successfully", "Success", tickets, HttpStatus.OK);
        } catch (InvalidInputException invalidInputExceptionError) {
            clientResponse = new ResponseDTO(invalidInputExceptionError.getMessage(), "Failed", ticketDTO, HttpStatus.BAD_REQUEST);
        } catch (Exception error){
            clientResponse = new ResponseDTO(error.getMessage(), "Failed", ticketDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("Completed search ticket");
        return new ResponseEntity<>(clientResponse, clientResponse.getHttpStatus());
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addTicket(@RequestBody TicketDTO ticketDTO) {
        logger.info("Starting add ticket execution\n TicketDTO : {}", ticketDTO);

        ResponseDTO clientResponse;
        try {
            boolean validationResponse = ticketValidator.addTicketValidator(ticketDTO);
            List<Ticket> addedTicket = List.of(ticketService.saveTicket(ticketDTO));
            clientResponse = new ResponseDTO("Tickets added successfully", "Success", addedTicket, HttpStatus.OK);
        } catch (InvalidInputException invalidInputExceptionError) {
            clientResponse = new ResponseDTO(invalidInputExceptionError.getMessage(), "Failed", ticketDTO, HttpStatus.BAD_REQUEST);
        } catch (Exception error){
            clientResponse = new ResponseDTO(error.getMessage(), "Failed", ticketDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("Completed add ticket");
        return new ResponseEntity<>(clientResponse, clientResponse.getHttpStatus());
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateTicket(@RequestBody TicketDTO ticketDTO) {
        logger.info("Starting update ticket execution\n TicketDTO : {}", ticketDTO);

        ResponseDTO clientResponse;
        try {
            boolean validationResponse = ticketValidator.updateTicketValidator(ticketDTO);
            List<Ticket> updatedTicket = List.of(ticketService.updateTicket(ticketDTO));
            clientResponse = new ResponseDTO("Tickets updated successfully", "Success", updatedTicket, HttpStatus.OK);
        } catch (InvalidInputException invalidInputExceptionError) {
            clientResponse = new ResponseDTO(invalidInputExceptionError.getMessage(), "Failed", ticketDTO, HttpStatus.BAD_REQUEST);
        } catch (Exception error){
            clientResponse = new ResponseDTO(error.getMessage(), "Failed", ticketDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("Completed updating ticket");
        return new ResponseEntity<>(clientResponse, clientResponse.getHttpStatus());
    }
}
