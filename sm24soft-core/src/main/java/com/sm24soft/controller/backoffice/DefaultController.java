package com.sm24soft.controller.backoffice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
	
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody HttpResponse<String> updateActivingMenuItem(
			@RequestParam(value = "act_menu", required = false, defaultValue = "") String activeMenuItem, 
			@RequestParam(value = "act_child_menu", required = false, defaultValue = "") String activeChildMenuItem, 
			final HttpServletRequest request) {
		logger.info("Call updateActivingMenuItem()");
		
		final HttpSession session = request.getSession();
		if (StringUtils.isNotEmpty(activeMenuItem)) {
			session.setAttribute(PageComponent.DEFAULT_ACTIVING_MENU_ITEM, activeMenuItem);
			session.removeAttribute(PageComponent.DEFAULT_ACTIVING_CHILD_MENU_ITEM);
		}
		
		if (StringUtils.isNotEmpty(activeChildMenuItem)) {
			session.setAttribute(PageComponent.DEFAULT_ACTIVING_CHILD_MENU_ITEM, activeChildMenuItem);
		}
		return new HttpResponse<String>(HttpStatus.OK, null, null);
	}
}
