package me.kakao.pay.luck.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import me.kakao.pay.common.domain.Luck;
import me.kakao.pay.common.domain.LuckRecord;
import me.kakao.pay.common.domain.LuckyMember;
import me.kakao.pay.luck.vo.BlessLuckRequest;
import me.kakao.pay.luck.vo.LuckRecordsResponse;
import me.kakao.pay.luck.vo.LuckyMemberResponse;

@Mapper(componentModel = "spring")
public interface LuckObjectMapper {
	@Mappings({ @Mapping(target = "blesserId", source = "userId", defaultValue = ""), @Mapping(target = "roomId", source = "roomId", defaultValue = "") })
	Luck blessRequestToLuck(BlessLuckRequest request, String userId, String roomId);

	@Mappings({ @Mapping(target = "blesserId", source = "userId", defaultValue = ""), @Mapping(target = "roomId", source = "roomId", defaultValue = "") })
	Luck recordsRequestToLuck(String token, String userId, String roomId);

	default LuckRecordsResponse luckRecordsToResponse(LuckRecord luckRecords) {
		List<LuckyMemberResponse> luckyMembers = new ArrayList<>();
		for (LuckyMember luckRecord : luckRecords.getLuckyMembers()) {
			luckyMembers.add(luckRecordToResponse(luckRecord));
		}

		LuckRecordsResponse response = new LuckRecordsResponse();
		response.setBlessAmount(luckRecords.getAmount());
		response.setTotalGrabbedAmount(luckRecords.getTotalGrabbedAmount());
		response.setTotalLuckyMemberCount(luckRecords.getTotalLuckyMemberCount());
		response.setLuckyMembers(luckyMembers);
		response.setBlessTime(luckRecords.getBlessTime());
		response.setBlessDate(luckRecords.getBlessDate());
		return response;
	}

	LuckyMemberResponse luckRecordToResponse(LuckyMember luckRecord);
}
