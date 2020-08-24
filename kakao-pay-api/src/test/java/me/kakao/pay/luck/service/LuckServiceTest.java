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

	@Test
	public void testIsNotValidMember() {
		String roomId = "xd334";
		Luck luck = new Luck();
		luck.setRoomId(roomId);
		luckService.isNotValidMember(luck.getRoomId(), roomId);
	}

	@Test
	public void testIsExistGrab() {
		String requestUserId = "11123";
		Luck luck = new Luck();
		luck.setSeq(27);
		assertTrue(luckService.isDuplicatedGrab(luck.getSeq(), requestUserId));
	}

	@Test
	public void testIsBlesser() {
		String requestUserId = "test_user";
		Luck luck = new Luck();
		luck.setToken("YVS");
		luck.setRoomId("test_room");
		assertTrue(luckService.isBlesser(luck, requestUserId));
	}

	@Test
	public void testIsOverTime() {
		Luck luck = new Luck();
		luck.setRoomId("test_room");
		luck.setToken("AGr");
		luck.setExpiredMinutes(10);
		assertTrue(luckService.isOverTime(luck));
	}

	@Test
	public void testIsFullGrab() {
		Luck luck = new Luck();
		luck.setSeq(27);
		luck.setMaxGrabberCount(3);
		assertTrue(luckService.isFullGrab(luck));
	}

	@Test
	public void testIsOverDate() {
		Luck luck = new Luck();
		luck.setRoomId("X123");
		luck.setToken("1Oe");
		luck.setExpiredDays(7);
		assertTrue(luckService.isOverDate(luck));
	}

}
