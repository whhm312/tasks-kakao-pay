package me.kakao.pay.common.exception;

public class FailedCreateTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FailedCreateTokenException() {
		super();
	}

	public FailedCreateTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public FailedCreateTokenException(String message) {
		super(message);
	}

	public FailedCreateTokenException(Throwable cause) {
		super(cause);
	}
}
