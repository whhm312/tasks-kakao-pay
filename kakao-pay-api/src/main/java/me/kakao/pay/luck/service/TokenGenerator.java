package me.kakao.pay.luck.service;

import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

	public static String get() {
		return get(3);
	}

	public static String get(int tokenLength) {
		int length = ALPHA_NUMERIC_STRING.length();
		StringBuilder sb = new StringBuilder(tokenLength);
		for (int i = 0; i < tokenLength; i++) {
			int index = (int) (length * Math.random());
			sb.append(ALPHA_NUMERIC_STRING.charAt(index));
		}
		return sb.toString();
	}

}
