package com.sm24soft.security.web.access;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

public class Sm24SoftAccessDeniedHandler extends AccessDeniedHandlerImpl {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
			throws IOException, ServletException {
		// Will be displayed the access denied page
		setErrorPage("/accessdenied");
		
		// Any time a user tries to access a part of the application that they
		// do not have rights to lock their account
		super.handle(request, response, exception);
	}

}
