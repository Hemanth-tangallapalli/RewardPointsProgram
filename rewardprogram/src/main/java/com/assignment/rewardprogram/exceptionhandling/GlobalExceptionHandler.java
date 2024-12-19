package com.assignment.rewardprogram.exceptionhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
		logger.error(e.getMessage());
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleDateTimeParseException(HttpMessageNotReadableException e) {
		logger.error(e.getMessage());
		return new ResponseEntity<String>("Enter date in the format yyyy-MM-dd", HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
		logger.error(e.getMessage());
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		logger.error(e.getMessage());
		return new ResponseEntity<String>("An unexpected error occured:" + e.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
