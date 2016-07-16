package com.sm24soft.controller.backoffice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sm24soft.component.IAuthenticationFacade;
import com.sm24soft.controller.ApplicationController;
import com.sm24soft.controller.Controllable;

@org.springframework.stereotype.Controller
@RequestMapping("/admin")
public class DefaultController extends ApplicationController implements Controllable {

	private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);
	
	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	public DefaultController(IAuthenticationFacade authenticationFacade) {
		this.authenticationFacade = authenticationFacade;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String renderBackOfficeLoginPage() {
		logger.info("Call renderBackOfficeLoginPage()");
		
		Authentication authentication = authenticationFacade.getAuthentication();
		if (null != authentication && authentication.isAuthenticated()) {
			return "back-office/index";
		}
		return "back-office/authen/login";
	}
}
