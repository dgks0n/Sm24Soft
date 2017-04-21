package com.sm24soft.component;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;

public class AuthenticationComponent extends Component {

	private IAuthenticationFacade authenticationFacade;
	
	public AuthenticationComponent(IAuthenticationFacade authenticationFacade) {
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
