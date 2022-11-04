package com.order.orderservice.exception;

import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleDuplicateIdException(DuplicateKeyException duplicateKeyException) {
    	@SuppressWarnings("static-access")
		ErrorResponse errorResponse = new ErrorResponse().builder().message("Order with same order id already present").status(HttpStatus.CONFLICT).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleException(WebExchangeBindException e) {
    String mess =	e.getBindingResult()
    		.getAllErrors().stream()
    		.map(DefaultMessageSourceResolvable::getDefaultMessage)
    		.sorted().collect(Collectors.joining(","));
    	 ErrorResponse errorResponse =  ErrorResponse.builder().message(mess).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(errorResponse,new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    

    @ExceptionHandler(DataAccessResourceFailureException.class)
    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessResourceFailureException dataAccessResourceFailureException) {
    	  @SuppressWarnings("static-access")
		ErrorResponse errorResponse = new ErrorResponse().builder().message("Error connecting with Database").status(HttpStatus.REQUEST_TIMEOUT).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
    	@SuppressWarnings("static-access")
		ErrorResponse errorResponse = new ErrorResponse().builder().message("Internal Server Error").status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(DataNotFoundException ex) {
    	@SuppressWarnings("static-access")
		ErrorResponse errorResponse = new ErrorResponse().builder().message("Order details is not found").status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
