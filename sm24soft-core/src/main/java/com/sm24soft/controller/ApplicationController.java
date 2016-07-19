package com.sm24soft.controller;

import org.springframework.http.HttpStatus;

import com.sm24soft.http.response.HttpException;
import com.sm24soft.http.response.HttpResponse;

public abstract class ApplicationController {
	
	/**
	 * Create new HttpException for response
	 * 
	 * @param ex
	 * @return
	 */
	private <E extends Exception> HttpException getResponseError(E ex) {
		return new HttpException(ex.hashCode(), ex.getMessage(), ex);
	}
	
	/**
	 * Create new OK status for response
	 * 
	 * @return
	 */
	protected <T> HttpResponse<T> getOKStatus() {
		return new HttpResponse<T>(HttpStatus.OK, null, null);
	}
	
	protected <T> HttpResponse<T> getOKStatus(T result) {
		return new HttpResponse<T>(HttpStatus.OK, null, result);
	}
	
	/**
	 * Create new Error status for response
	 * 
	 * @param ex
	 * @return
	 */
	protected <E extends Exception, T> HttpResponse<T> getErrorStatus(E ex) {
		if (null == ex) {
			return getErrorStatus(); 
		}
		return new HttpResponse<T>(HttpStatus.INTERNAL_SERVER_ERROR, 
				getResponseError(ex), null); 
	}
	
	/**
	 * Create new Error status for response
	 * 
	 * @return
	 */
	protected <T> HttpResponse<T> getErrorStatus() {
		return new HttpResponse<T>(HttpStatus.INTERNAL_SERVER_ERROR, null, null);
	}
	
	protected String getRedirectTo404Page() {
		return "redirect:/error/404";
	}
	
	protected String getRedirectTo403Page() {
		return "redirect:/error/403";
	}
	
	protected String getRedirectTo500Page() {
		return "redirect:/error/500";
	}
}
