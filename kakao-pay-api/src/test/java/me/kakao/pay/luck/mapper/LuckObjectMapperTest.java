package me.kakao.pay.luck.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import me.kakao.pay.common.domain.Luck;
import me.kakao.pay.luck.mapper.LuckObjectMapper;
import me.kakao.pay.luck.vo.BlessLuckRequest;

public class LuckObjectMapperTest {
	private LuckObjectMapper luckMapping = Mappers.getMapper(LuckObjectMapper.class);

	@Test
	public void requestToBlessing() {
		int maxGrabberCount = 3;
		long amount = 300000;

		BlessLuckRequest request = new BlessLuckRequest();
		request.setAmount(amount);
		request.setMaxGrabberCount(maxGrabberCount);

		String userId = "xxxx";
		String roomId = "102";

		Luck blessing = luckMapping.requestToBlessing(request, userId, roomId);
		assertEquals(userId, blessing.getBlesserId());
		assertEquals(roomId, blessing.getRoomId());
		assertEquals(request.getAmount(), blessing.getAmount());
		assertEquals(request.getMaxGrabberCount(), blessing.getMaxGrabberCount());
	}

}
