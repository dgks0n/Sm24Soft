package com.sm24soft.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller
public class GlobalExceptionHandleController extends ApplicationController implements Controllable {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandleController.class);
	
	@RequestMapping(value = { "/error/500" }, method = RequestMethod.GET)
	public String handleInternalServerError(IOException ex) {
		logger.info("Call handleInternalServerError()");
		return "error/500";
	}
	
	@RequestMapping(value = { "/error/404" }, method = RequestMethod.GET)
	public String handleNotFoundError() {
		logger.info("Call handleNotFoundError()");
		return "error/404";
	}

}
