package me.kakao.pay.common.exception;

public class InvalidLuckException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidLuckException() {
		super();
	}

	public InvalidLuckException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidLuckException(String message) {
		super(message);
	}

	public InvalidLuckException(Throwable cause) {
		super(cause);
	}
}
