package com.sm24soft.http.response;

import org.springframework.http.HttpStatus;

public class HttpResponse<T> {
	
	private final int status;
	private final HttpException error;
	private final T result;

	public HttpResponse(final HttpStatus state, final HttpException error, final T result) {
		this.status = state.value();
		this.error = error;
		this.result = result;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @return the error
	 */
	public HttpException getError() {
		return error;
	}

	/**
	 * @return the result
	 */
	public T getResult() {
		return result;
	}

	@Override
	public String toString() {
		return "HttpResponse [getStatus()=" + getStatus() + ", getError()=" + getError() + ", getResult()="
				+ getResult() + "]";
	}

}
