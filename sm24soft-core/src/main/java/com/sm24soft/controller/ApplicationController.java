package com.sm24soft.controller;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import com.sm24soft.http.response.HttpException;
import com.sm24soft.http.response.HttpResponse;
import com.sm24soft.util.FileUtil;

@Controller
public class ApplicationController implements Controllable, ErrorControllable, Context {
	
	@Autowired
	private ServletContext context;
	
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
		return new HttpResponse<T>(HttpStatus.INTERNAL_SERVER_ERROR, getResponseError(ex), null); 
	}
	
	/**
	 * Create new Error status for response
	 * 
	 * @return
	 */
	protected <T> HttpResponse<T> getErrorStatus() {
		return new HttpResponse<T>(HttpStatus.INTERNAL_SERVER_ERROR, null, null);
	}
	
	protected String getContextPath() {
		return getContext().getRealPath(File.separator);
	}
	
	protected String getResourceDirectory() {
		return FileUtil.getResourceDirectory(getContextPath());
	}

	@Override
	public String redirectToError(final HttpStatus status) {
		return "redirect:/errors/" + status.value();
	}

	@Override
	public ServletContext getContext() {
		return this.context;
	}
	
}
