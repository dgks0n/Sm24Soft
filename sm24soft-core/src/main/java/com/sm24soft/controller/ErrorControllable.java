package com.sm24soft.controller;

import org.springframework.http.HttpStatus;

public interface ErrorControllable {
	
	String redirectToError(final HttpStatus status);
}
