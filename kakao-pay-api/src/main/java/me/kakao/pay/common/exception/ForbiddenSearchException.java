package me.kakao.pay.common.exception;

public class ForbiddenSearchException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ForbiddenSearchException() {
		super();
	}

	public ForbiddenSearchException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForbiddenSearchException(String message) {
		super(message);
	}

	public ForbiddenSearchException(Throwable cause) {
		super(cause);
	}
}
