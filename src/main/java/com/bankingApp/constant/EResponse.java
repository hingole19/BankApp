package com.bankingApp.constant;

import org.springframework.http.HttpStatus.Series;

public enum EResponse {

	SUCCESS(200, "SUCCESS"), FAILED(424, "FAILED"),UNAUTHORIZED(401,"Invalid Username and Password"),
	BAD_REQUEST(400, "Bad Request");

	private int code;
	private String message;

	private EResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	EResponse(int l) {
		this.code = l;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
