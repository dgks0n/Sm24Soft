package com.sm24soft.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import com.sm24soft.component.IAuthenticationFacade;

@org.springframework.stereotype.Component("AuthenticationInterceptor")
public class AuthenticationInterceptor {

	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	public AuthenticationInterceptor(IAuthenticationFacade authenticationFacade) {
		this.authenticationFacade = authenticationFacade;
	}
	
	public String getCurrentUserName() {
		Authentication authentication = authenticationFacade.getAuthentication();
		if (null != authentication) {
		    String currentUserName = authentication.getName();
		    if (StringUtils.isNotEmpty(currentUserName)) {
		    	currentUserName = StringUtils.capitalize(currentUserName);
		    }
		    return currentUserName;
		}
		return null;
	}
}
