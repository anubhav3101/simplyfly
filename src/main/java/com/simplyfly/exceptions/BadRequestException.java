package com.simplyfly.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    public BadRequestException(HttpStatus badRequest, String message) {
        super(message);
    }

	public BadRequestException(String string) {
		// TODO Auto-generated constructor stub
	}
}
