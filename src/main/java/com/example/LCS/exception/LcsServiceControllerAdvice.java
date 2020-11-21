package com.example.LCS.exception;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.LCS.model.ErrorModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class LcsServiceControllerAdvice extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Invalid request parameter(s)", ex);

		String exceptionMessage = ex.getBindingResult().getAllErrors().stream().map(n -> n.getDefaultMessage())
				.collect(Collectors.joining(" , ", "[", "]"));

		ErrorModel response = new ErrorModel(String.valueOf(HttpStatus.BAD_REQUEST), exceptionMessage);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error("Invalid request parameter(s)", ex);

		ErrorModel response = new ErrorModel(String.valueOf(HttpStatus.BAD_REQUEST), ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationError(ConstraintViolationException ex) {

		log.error("Bad request for lcs service", ex);

		ErrorModel response = new ErrorModel(String.valueOf(HttpStatus.BAD_REQUEST), ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LcsServiceValidationException.class)
	public ResponseEntity<?> handleConstraintViolationError(LcsServiceValidationException ex) {

		log.error("Bad request, values are not distinct", ex);

		ErrorModel response = new ErrorModel(String.valueOf(HttpStatus.BAD_REQUEST), ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LcsServiceException.class)
	public ResponseEntity<?> handleConstraintViolationError(LcsServiceException ex) {

		log.error("Runtime exception has occurred", ex);

		ErrorModel response = new ErrorModel(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
