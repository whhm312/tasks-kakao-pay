package me.kakao.pay.common.exception;

public class DuplicatedTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicatedTokenException() {
		super();
	}

	public DuplicatedTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatedTokenException(String message) {
		super(message);
	}

	public DuplicatedTokenException(Throwable cause) {
		super(cause);
	}
}
