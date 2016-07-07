package com.sm24soft.controller.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sm24soft.component.IAuthenticationFacade;
import com.sm24soft.controller.Controllable;
import com.sm24soft.controller.Controller;

@org.springframework.stereotype.Controller
@RequestMapping("/admin")
public class DefaultAdminController extends Controller implements Controllable {

	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	public DefaultAdminController(IAuthenticationFacade authenticationFacade) {
		this.authenticationFacade = authenticationFacade;
	}
	
	@RequestMapping(path = { "", "/" }, method = RequestMethod.GET)
	public String renderBOLoginPage() {
		Authentication authentication = authenticationFacade.getAuthentication();
		if (null != authentication && authentication.isAuthenticated()) {
			return "bo/index";
		}
		return "bo/authen/login";
	}

	@RequestMapping(path = { "/index", "/home" }, method = RequestMethod.GET)
	public String renderAdministrationIndexPage() {
		return "bo/index";
	}
}
