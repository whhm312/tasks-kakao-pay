package me.kakao.pay.common.exception;

public class NotValidMemberException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotValidMemberException() {
		super();
	}

	public NotValidMemberException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotValidMemberException(String message) {
		super(message);
	}

	public NotValidMemberException(Throwable cause) {
		super(cause);
	}

}
