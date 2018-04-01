package com.admin.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e) {
		logger.error("Error caught by global exception handler", e);
		return "pageError";
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNotFoundException(Exception e) {
        logger.error("A null pointer exception ocurred " , e);
        return "pageNotFound";
    }
	
	@ExceptionHandler(javax.el.PropertyNotFoundException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleErrorException(Exception e) {
        logger.error("An exception ocurred " , e);
        return "pageError";
    }
	
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public String handleMethodNotAllowedException(Exception e) {
        logger.error("A null pointer exception ocurred " , e);
        return "pageError";
    }
}
