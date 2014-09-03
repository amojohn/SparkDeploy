package com.hansight.deploy.vo;

import org.apache.commons.lang.exception.ExceptionUtils;

public class Status {
	private boolean success;
	private String info;

	private Status(boolean result) {
		this.success = result;
	}

	private Status(boolean result, String message) {
		this.success = result;
		if (message != null) {
			this.info = message.trim();
		}
	}

	private Status(boolean result, Exception message) {
		this.success = result;
		this.info = ExceptionUtils.getFullStackTrace(message);
	}

	public static Status success() {
		return new Status(true);
	}

	public static Status status(boolean status) {
		return new Status(status);
	}

	public static Status success(String message) {
		return new Status(true, message);
	}

	public static Status failure(String message) {
		return new Status(false, message);
	}

	public static Status failure() {
		return new Status(false);
	}

	public static Status failure(Exception message) {
		return new Status(false, message);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return info;
	}

	public void setMessage(String message) {
		this.info = message;
	}

	public void prepend(String message) {
		this.info = message + ";\t" + this.info;
	}

	@Override
	public String toString() {
		return "Status:" + (success ? "success" : "failure") + ", message:"
				+ info;
	}

}