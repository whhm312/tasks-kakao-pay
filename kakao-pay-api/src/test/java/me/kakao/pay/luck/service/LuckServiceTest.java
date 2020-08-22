package me.kakao.pay.luck.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.kakao.pay.common.domain.Luck;

@SpringBootTest
public class LuckServiceTest {

	@Autowired
	LuckService luckService;

	@Test
	public void testUniqueToken() {
		Luck blessing = new Luck();
		blessing.setToken(TokenGenerator.get());
		blessing.setAmount(100000);
		blessing.setBlesserId("test1");
		blessing.setMaxGrabberCount(2);
		blessing.setRoomId("xx1123");

		assertTrue(luckService.isUniqueToken(blessing));

		String token = luckService.blessing(blessing);
		blessing.setToken(token);

		assertFalse(luckService.isUniqueToken(blessing));
	}
}
