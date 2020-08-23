package me.kakao.pay.common.exception;

public class FullGrabException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FullGrabException() {
		super();
	}

	public FullGrabException(String message, Throwable cause) {
		super(message, cause);
	}

	public FullGrabException(String message) {
		super(message);
	}

	public FullGrabException(Throwable cause) {
		super(cause);
	}
}
