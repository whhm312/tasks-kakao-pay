package me.kakao.pay.luck.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.kakao.pay.common.domain.Luck;
import me.kakao.pay.common.domain.LuckDetail;
import me.kakao.pay.luck.mapper.LuckDBMapper;

@SpringBootTest
public class LuckServiceTest {

	@Autowired
	LuckService luckService;

	@Autowired
	LuckDBMapper luckDBMapper;

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

	@Test
	public void testSelectLuck() {
		Luck luck = new Luck();
		luck.setAmount(1000000);
		luck.setBlesserId("abcd");
		luck.setRoomId("abc0");
		luck.setMaxGrabberCount(3);
		luck.setToken("f24");

		Luck selectedLuck = luckDBMapper.selectLuck(luck);

		assertEquals(luck.getAmount(), selectedLuck.getAmount());
		assertEquals(luck.getBlesserId(), selectedLuck.getBlesserId());
		assertEquals(luck.getMaxGrabberCount(), selectedLuck.getMaxGrabberCount());
		assertEquals(luck.getRoomId(), selectedLuck.getRoomId());
		assertEquals(luck.getToken(), selectedLuck.getToken());
		assertNotNull(selectedLuck.getBlessingDate());
	}

	@Test
	public void testBlessing() {
		Luck luck = new Luck();
		luck.setAmount(1000000);
		luck.setBlesserId("abcd");
		luck.setMaxGrabberCount(3);
		luck.setRoomId("abc0");
		luck.setToken(TokenGenerator.get());
		luckService.blessing(luck);

		Luck selectedLuck = luckDBMapper.selectLuck(luck);
		assertEquals(luck.getAmount(), selectedLuck.getAmount());
		assertEquals(luck.getBlesserId(), selectedLuck.getBlesserId());
		assertEquals(luck.getMaxGrabberCount(), selectedLuck.getMaxGrabberCount());
		assertEquals(luck.getRoomId(), selectedLuck.getRoomId());
		assertEquals(luck.getToken(), selectedLuck.getToken());

		List<LuckDetail> selectedLuckDetails = luckDBMapper.selectLuckDetail(luck);
		assertEquals(luck.getMaxGrabberCount(), selectedLuckDetails.size());
		long totalAmount = 0;
		for (LuckDetail luckDetail : selectedLuckDetails) {
			assertEquals(luck.getSeq(), luckDetail.getLuckSeq());
			totalAmount += luckDetail.getAmount();
		}

		assertEquals(luck.getAmount(), totalAmount);
	}
}
