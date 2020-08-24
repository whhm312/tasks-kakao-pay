package me.kakao.pay.common.exception;

public class ExpiredTimeLuckException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExpiredTimeLuckException() {
		super();
	}

	public ExpiredTimeLuckException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpiredTimeLuckException(String message) {
		super(message);
	}

	public ExpiredTimeLuckException(Throwable cause) {
		super(cause);
	}
}
