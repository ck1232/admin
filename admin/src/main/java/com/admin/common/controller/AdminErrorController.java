package com.admin.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminErrorController implements ErrorController {
	private final static String ERROR_PATH = "/error";
	
	@RequestMapping(value = ERROR_PATH)
    public String errorHtml(HttpServletRequest request) {
		HttpStatus status = getStatus(request);
		switch(status) {
		case NOT_FOUND : return "pageNotFound";
		default: return "pageError";
		}
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
