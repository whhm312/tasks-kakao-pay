package me.kakao.pay.common.exception;

public class FailedInsertLuckDetailException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FailedInsertLuckDetailException() {
		super();
	}

	public FailedInsertLuckDetailException(String message, Throwable cause) {
		super(message, cause);
	}

	public FailedInsertLuckDetailException(String message) {
		super(message);
	}

	public FailedInsertLuckDetailException(Throwable cause) {
		super(cause);
	}
}
