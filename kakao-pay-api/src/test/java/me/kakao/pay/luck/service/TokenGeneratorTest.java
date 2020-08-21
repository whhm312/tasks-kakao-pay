package me.kakao.pay.luck.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class TokenGeneratorTest {

	@Test
	public void testGenerate() {
		for (int i = 0; i < 5; i++) {
			System.out.println(TokenGenerator.get(3));
		}

		assertNotNull(TokenGenerator.get(3));
	}
}
