package com.kapture.ticketservice.dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String clientId;
	private String ticketCode;
	private String title = "general-ticket";
	private String status;
	private Timestamp timestamp;
	private int limit;
	private Date startDate;
	private Date endDate;
	private int page;
}
