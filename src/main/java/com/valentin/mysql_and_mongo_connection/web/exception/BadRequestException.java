package com.valentin.mysql_and_mongo_connection.web.exception;

public class BadRequestException extends RuntimeException {
	public BadRequestException(String message) {
		super(message);
	}
}
