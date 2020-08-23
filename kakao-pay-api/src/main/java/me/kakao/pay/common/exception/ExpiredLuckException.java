package me.kakao.pay.common.exception;

public class ExpiredLuckException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExpiredLuckException() {
		super();
	}

	public ExpiredLuckException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpiredLuckException(String message) {
		super(message);
	}

	public ExpiredLuckException(Throwable cause) {
		super(cause);
	}
}
