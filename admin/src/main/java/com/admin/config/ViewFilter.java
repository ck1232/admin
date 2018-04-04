package com.admin.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;
@Component
public class ViewFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
	        chain.doFilter(request, response);
	    }
	    catch (Exception ex) {
	        throw new ServletException(ex);
	    }
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
