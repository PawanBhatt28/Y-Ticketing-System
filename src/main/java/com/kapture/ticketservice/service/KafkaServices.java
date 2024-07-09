package com.kapture.ticketservice.service;

import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.kapture.ticketservice.constants.Constants;
import com.kapture.ticketservice.dto.TicketDTO;

@Service
public class KafkaServices implements Constants {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private KafkaTemplate<String, Object> kafkaTemplate;
	private TicketService ticketService;

	public KafkaServices(KafkaTemplate<String, Object> kafkaTemplate, TicketService ticketService) {
		this.kafkaTemplate = kafkaTemplate;
		this.ticketService = ticketService;
	}

	public void produceTicket(TicketDTO ticketDTO) {

		CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(kafkaTopic, ticketDTO);
		future.whenComplete((result, exception) -> {
			if (exception != null) {
				logger.info("Error in pushing the ticket in the Kafka Server ", exception);
			} else {
				logger.info("Successfully pushed ticket in the Kafka Server"+ticketDTO.toString());
			}
		});
	}

	@KafkaListener(topics = kafkaTopic, groupId = listenerGroup)
	public void consumeTicket(TicketDTO ticketDTO) {
		Object ticket = null;
		try {
		ticket = ticketService.saveTicket(ticketDTO);
		} catch (Exception e) {
			logger.info("Error in Listening the tickets");
			return;
		} finally {
			if(ticket !=  null)
				logger.info("Listener Consumed the ticket "+ticket.toString());
		}
	}

}
