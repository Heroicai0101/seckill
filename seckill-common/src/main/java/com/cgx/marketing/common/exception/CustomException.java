package com.cgx.marketing.common.exception;

import com.cgx.marketing.common.enums.Status;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = -1629784989090820983L;

	private static final String SPLIT = "/";

	public CustomException(int status, String errorCode, String errorMessage) {
		super(status + SPLIT + errorCode + SPLIT + errorMessage);
	}

	public CustomException(Status status) {
		this(status.getStatus(), status.getCode(), status.getMsg());
	}

	public CustomException(Status status, Object... format) {
		this(status.getStatus(), status.getCode(), String.format(status.getMsg(), format));
	}

	public String getCode() {
		return splitMessage(1);
	}

	public String getMsg() {
		return splitMessage(2);
	}

	public int getStatus() {
		String status = splitMessage(0);
		return (status != null && status.length() > 0) ? Integer.valueOf(status) : 0;
	}

	private String splitMessage(int index) {
		String message = getMessage();
		if (message != null && message.contains(SPLIT)) {
			String[] split = message.split(SPLIT);
			if (split.length >= index) {
				return split[index];
			}
		}
		return "";
	}
}
