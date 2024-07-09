package com.kapture.ticketservice.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="ticket")
public class Ticket implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="client_id")
	private int clientId;
	
	@Column(name="ticket_code", unique = true)
	private int ticketCode;
	
	@Column(name="title")
	private String title = "General";
	
	@Column(name="last_modified_date")
	private Timestamp lastModifiedDate;
	
	@Column(name="status")
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getTicket_code() {
		return ticketCode;
	}

	public void setTicket_code(int ticket_code) {
		this.ticketCode = ticket_code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", clientId=" + clientId + ", ticket_code=" + ticketCode + ", title=" + title
				+ ", lastModifiedDate=" + lastModifiedDate + ", status=" + status + "]";
	}
	
}
