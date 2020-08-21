package me.kakao.pay.luck.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.kakao.pay.common.domain.Blessing;
import me.kakao.pay.luck.service.TokenGenerator;

@SpringBootTest
public class LuckDAOTest {

	@Autowired
	private LuckDAO luckDAO;

	@Test
	public void testConnection() {
		String expected = LocalDate.now().toString();
		assertEquals(expected, luckDAO.now());
	}

	@Test
	public void testInsertBless() {
		String roomId = "X123";
		String blesserId = "whily312";
		int maxReceiverCount = 3;
		long amount = 30000;
		String token = TokenGenerator.get();

		Blessing blessing = new Blessing();
		blessing.setAmount(amount);
		blessing.setMaxReceiverCount(maxReceiverCount);
		blessing.setBlesserId(blesserId);
		blessing.setRoomId(roomId);
		blessing.setToken(token);

		assertEquals(1, luckDAO.insert(blessing));
	}

}
