package com.kapture.ticketservice.dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class TicketDTO implements Serializable  {
	private static final long serialVersionUID = 1L;
	Integer clientId;
	Integer ticketCode;
	String title;
	String status;
	Timestamp timestamp;
	int limit;
	Date startDate;
	Date endDate;
	int page;
	public TicketDTO() {
	}
	public TicketDTO(int clientId, int ticketCode, String title, String status, int limit, Timestamp timestamp, Date start, Date end, int page) {
		this.clientId = clientId;
		this.ticketCode = ticketCode;
		this.title = title;
		this.status = status;
		this.limit = limit;
		this.timestamp = timestamp;
		this.startDate = start;
		this.endDate = end;
		this.page = page;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Integer getTicketCode() {
		return ticketCode;
	}
	public void setTicketCode(Integer ticketCode) {
		this.ticketCode = ticketCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
}
