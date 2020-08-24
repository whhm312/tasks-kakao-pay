package me.kakao.pay.common.exception;

public class ExpiredDateLuckException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExpiredDateLuckException() {
		super();
	}

	public ExpiredDateLuckException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpiredDateLuckException(String message) {
		super(message);
	}

	public ExpiredDateLuckException(Throwable cause) {
		super(cause);
	}
}
