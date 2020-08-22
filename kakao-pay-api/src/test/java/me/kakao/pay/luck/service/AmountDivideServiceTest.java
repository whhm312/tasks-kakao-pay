package me.kakao.pay.luck.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class AmountDivideServiceTest {

	@Test
	public void testGenerate() {
		long totalAmount = 123404;
		int max = 5;
		List<Long> divide = AmountDivideService.divide(totalAmount, max);

		System.out.println(divide.toString());

		assertEquals(max, divide.size());
	}
}
