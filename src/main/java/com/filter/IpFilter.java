package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
@Component
public class IpFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String uri = ((HttpServletRequest)request).getRequestURI();
		if(uri.contains("signup")) {
				///
		}
		System.out.println("checking ip address......filter");
		chain.doFilter(request, response);
	}

}
