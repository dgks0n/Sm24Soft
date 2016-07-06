package com.sm24soft.controller.fo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sm24soft.controller.Controllable;
import com.sm24soft.controller.Controller;

@org.springframework.stereotype.Controller("HomeController")
public class HomeController extends Controller implements Controllable {

	public HomeController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(path = { "/", "/Home" }, method = RequestMethod.GET)
	public String renderHomePage() {
		return "Home";
	}
}
