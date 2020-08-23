package me.kakao.pay.luck.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LuckRecordsResponse {
	private String blessTime;
	private long blessAmount;
	private long totalGrabbedAmount;
	private int totalLuckyMemberCount;
	private List<LuckyMemberResponse> luckyMembers;
}
