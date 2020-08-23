package me.kakao.pay.common.exception;

public class BlesserNotAllowGrabException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BlesserNotAllowGrabException() {
		super();
	}

	public BlesserNotAllowGrabException(String message, Throwable cause) {
		super(message, cause);
	}

	public BlesserNotAllowGrabException(String message) {
		super(message);
	}

	public BlesserNotAllowGrabException(Throwable cause) {
		super(cause);
	}
}
