package com.admin.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class AdminErrorController implements ErrorController {
	private ErrorAttributes errorAttributes;
	private final static String ERROR_PATH = "/error";
	
	@Autowired
	public AdminErrorController(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}
	
	@RequestMapping(value = ERROR_PATH)
    public String errorHtml(HttpServletRequest request) {
		HttpStatus status = getStatus(request);
		switch(status) {
		case NOT_FOUND : return "pageNotFound";
		default: return "pageError";
		}
    }
	
	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return this.errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
	}

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode != null) {
			try {
				return HttpStatus.valueOf(statusCode);
			}catch (Exception ex) {
			}
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}
