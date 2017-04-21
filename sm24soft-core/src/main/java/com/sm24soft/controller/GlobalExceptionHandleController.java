package com.sm24soft.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller
public class GlobalExceptionHandleController {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandleController.class);
	
	@RequestMapping(value = { "/errors/{errorCode}" }, method = RequestMethod.GET)
	public String handleError(@PathVariable("errorCode") String errorCode, Exception ex) {
		logger.info("Call handleError()");
		
		if (ex != null) {
			logger.error(ex.getMessage(), ex);
		}
		
		return "errors/" + errorCode;
	}
	
}
