package me.kakao.pay.luck.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LuckRecordsResponse {
	private String blessTime;
	private String blessDate;
	private long blessAmount;
	private long totalGrabbedAmount;
	private int totalLuckyMemberCount;
	private List<LuckyMemberResponse> luckyMembers;
}
