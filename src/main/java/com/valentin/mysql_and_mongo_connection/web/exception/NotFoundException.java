package com.valentin.mysql_and_mongo_connection.web.exception;

public class NotFoundException extends RuntimeException {
	public NotFoundException(String message) {
		super(message);
	}
}
