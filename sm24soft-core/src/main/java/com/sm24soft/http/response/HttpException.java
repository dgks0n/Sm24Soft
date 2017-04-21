package com.sm24soft.http.response;

public class HttpException {

	private final int code;
	private final String message;
	private final Exception error;

	public HttpException(final int code, String message, Exception exception) {
		this.code = code;
		this.message = message;
		this.error = exception;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the error
	 */
	public Exception getError() {
		return error;
	}

	@Override
	public String toString() {
		return "HttpException [getCode()=" + getCode() + ", getMessage()=" + getMessage() + ", getError()=" + getError()
				+ "]";
	}
	
}
