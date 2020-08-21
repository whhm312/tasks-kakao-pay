package me.kakao.pay.luck.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import me.kakao.pay.common.domain.Blessing;
import me.kakao.pay.luck.mapper.LuckObjectMapper;
import me.kakao.pay.luck.vo.BlessRequest;

public class LuckObjectMapperTest {
	private LuckObjectMapper luckMapping = Mappers.getMapper(LuckObjectMapper.class);

	@Test
	public void requestToBlessing() {
		int maxReceiverCount = 3;
		long amount = 300000;

		BlessRequest request = new BlessRequest();
		request.setAmount(amount);
		request.setMaxReceiverCount(maxReceiverCount);

		String userId = "xxxx";
		String roomId = "102";

		Blessing blessing = luckMapping.requestToBlessing(request, userId, roomId);
		assertEquals(userId, blessing.getBlesserId());
		assertEquals(roomId, blessing.getRoomId());
		assertEquals(request.getAmount(), blessing.getAmount());
		assertEquals(request.getMaxReceiverCount(), blessing.getMaxReceiverCount());
	}

}
