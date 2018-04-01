package com.admin.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(HttpServletRequest request, Exception e) {
		logger.error("An Error occured for Method :"+ request.getMethod() + "not supported for URL:"+ request.getRequestURL(), e);
		return "pageError";
	}
	
	@ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
	public String methodNotSupportedException(HttpServletRequest request, Exception e) {
        logger.error("Method :"+ request.getMethod() + "not supported for URL:"+ request.getRequestURL(), e);
        return "pageError";
    }
}
