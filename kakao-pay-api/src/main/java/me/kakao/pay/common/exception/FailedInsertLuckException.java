package me.kakao.pay.common.exception;

public class FailedInsertLuckException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FailedInsertLuckException() {
		super();
	}

	public FailedInsertLuckException(String message, Throwable cause) {
		super(message, cause);
	}

	public FailedInsertLuckException(String message) {
		super(message);
	}

	public FailedInsertLuckException(Throwable cause) {
		super(cause);
	}
}
