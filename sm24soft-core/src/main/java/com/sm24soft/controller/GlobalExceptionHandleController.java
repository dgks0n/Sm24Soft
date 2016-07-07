package com.sm24soft.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller
public class GlobalExceptionHandleController extends Controller implements Controllable {

	@RequestMapping(value = { "/error/500" }, method = RequestMethod.GET)
	public String handleIOException(IOException ex) {
		return "error/500";
	}
	
	@RequestMapping(value = { "/error/404" }, method = RequestMethod.GET)
	public String handleResourceNotFoundException() {
		return "error/404";
	}

}
