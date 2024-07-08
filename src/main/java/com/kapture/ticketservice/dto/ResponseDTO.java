package com.kapture.ticketservice.dto;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseDTO {
    private String message;
    private String status;
    private Object object;
    private HttpStatus httpStatus;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	public ResponseDTO(String message, String status, Object object, HttpStatus httpStatus) {
		
		this.message = message;
		this.status = status;
		this.object = object;
		this.httpStatus = httpStatus;
	}
	public ResponseDTO() {
	}
	@Override
	public String toString() {
		return "ResponseDTO [message=" + message + ", status=" + status + ", object=" + object + ", httpStatus="
				+ httpStatus + "]";
	}
    
    
}