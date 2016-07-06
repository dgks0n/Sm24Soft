package com.sm24soft.controller.bo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sm24soft.controller.Controllable;
import com.sm24soft.controller.Controller;

@org.springframework.stereotype.Controller("LoginController")
public class LoginController extends Controller implements Controllable {

	public LoginController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(path = "/Login", method = RequestMethod.GET)
	public String renderLoginPage() {
		return "Login";
	}
}
