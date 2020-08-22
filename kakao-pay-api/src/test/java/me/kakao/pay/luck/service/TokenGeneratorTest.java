package me.kakao.pay.luck.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TokenGeneratorTest {

	@Test
	public void testGenerate() {
		String token = TokenGenerator.get();
		assertNotNull(token);
		assertTrue(token.length() == 3);
	}
}
