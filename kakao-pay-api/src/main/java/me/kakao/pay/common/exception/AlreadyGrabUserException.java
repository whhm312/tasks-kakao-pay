package me.kakao.pay.common.exception;

public class AlreadyGrabUserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AlreadyGrabUserException() {
		super();
	}

	public AlreadyGrabUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyGrabUserException(String message) {
		super(message);
	}

	public AlreadyGrabUserException(Throwable cause) {
		super(cause);
	}
}
