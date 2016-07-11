package com.sm24soft.controller.backoffice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sm24soft.component.IAuthenticationFacade;
import com.sm24soft.component.PageComponent;
import com.sm24soft.controller.ApplicationController;
import com.sm24soft.controller.Controllable;
import com.sm24soft.http.response.HttpResponse;

@org.springframework.stereotype.Controller
@RequestMapping("/admin")
public class DefaultAdministrativeController extends ApplicationController implements Controllable {

	private static final Logger logger = LoggerFactory.getLogger(DefaultAdministrativeController.class);
	
	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	public DefaultAdministrativeController(IAuthenticationFacade authenticationFacade) {
		this.authenticationFacade = authenticationFacade;
	}
	
	@RequestMapping(path = { "" }, method = RequestMethod.GET)
	public String renderBackOfficeLoginPage() {
		Authentication authentication = authenticationFacade.getAuthentication();
		logger.info("Call renderBackOfficeLoginPage()");
		if (null != authentication && authentication.isAuthenticated()) {
			return "back-office/index";
		}
		
		return "back-office/authen/login";
	}

	@RequestMapping(path = { "/index" }, method = RequestMethod.GET)
	public String renderAdministrationIndexPage() {
		logger.info("Call renderAdministrationIndexPage()");
		return "back-office/index";
	}
	
	@RequestMapping(path = { "/index" }, method = RequestMethod.POST)
	public @ResponseBody HttpResponse<String> updateActivingMenuItem(final HttpServletRequest request,
			@RequestParam("active_menu_item") String activeMenuItem) {
		logger.info("Call updateActivingMenuItem()");
		final HttpSession session = request.getSession();
		session.setAttribute(PageComponent.DEFAULT_ACTIVING_MENU_ITEM, activeMenuItem);
		return new HttpResponse<String>(HttpStatus.OK, null, null);
	}
}
