package com.assignment.rewardprogram.exceptionhandling;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException e) {
		logger.error(e.getMessage());
		return new ResponseEntity<ErrorDetails>(new ErrorDetails(LocalDateTime.now(), e.getMessage()),
				HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorDetails> handleDateTimeParseException(HttpMessageNotReadableException e) {
		logger.error(e.getMessage());
		return new ResponseEntity<ErrorDetails>(
				new ErrorDetails(LocalDateTime.now(), "Enter date in the format yyyy-MM-dd"), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorDetails> handleNullPointerException(NullPointerException e) {
		logger.error(e.getMessage());
		return new ResponseEntity<ErrorDetails>(new ErrorDetails(LocalDateTime.now(), e.getMessage()),
				HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorDetails> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException e) {
		logger.error(e.getMessage());
		if (e.getMessage().contains("customerId")) {
			return new ResponseEntity<ErrorDetails>(new ErrorDetails(LocalDateTime.now(), "Enter valid customerId"),
					HttpStatus.BAD_REQUEST);
		} else if (e.getMessage().contains("startDate") || e.getMessage().contains("endDate")) {
			return new ResponseEntity<ErrorDetails>(
					new ErrorDetails(LocalDateTime.now(), "Enter date in the format yyyy-MM-dd"),
					HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<ErrorDetails>(new ErrorDetails(LocalDateTime.now(), e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}

	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorDetails> handleMissingServletRequestParameterException(
			MissingServletRequestParameterException e) {
		logger.error(e.getMessage());
		if (e.getMessage().contains("customerId")) {
			return new ResponseEntity<ErrorDetails>(new ErrorDetails(LocalDateTime.now(), "Enter valid customerId"),
					HttpStatus.BAD_REQUEST);
		} else if (e.getMessage().contains("startDate") || e.getMessage().contains("endDate")) {
			return new ResponseEntity<ErrorDetails>(
					new ErrorDetails(LocalDateTime.now(), "Enter date in the format yyyy-MM-dd"),
					HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<ErrorDetails>(new ErrorDetails(LocalDateTime.now(), e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}

	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ErrorDetails> handleNoResourceFoundException(NoResourceFoundException e) {
		logger.error(e.getMessage());
		return new ResponseEntity<ErrorDetails>(
				new ErrorDetails(LocalDateTime.now(), "No resource present for the given customerId"),
				HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleException(Exception e) {
		logger.error(e.getMessage());
		return new ResponseEntity<ErrorDetails>(
				new ErrorDetails(LocalDateTime.now(), "An unexpected error occured:" + e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
