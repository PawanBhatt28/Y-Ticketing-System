package com.kapture.ticketservice.dto;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
	private String message;
	private String status;
	private Object object;
	private HttpStatus httpStatus;

	@Override
	public String toString() {
		return "ResponseDTO [message=" + message + ", status=" + status + ", object=" + object + ", httpStatus="
				+ httpStatus + "]";
	}
}
