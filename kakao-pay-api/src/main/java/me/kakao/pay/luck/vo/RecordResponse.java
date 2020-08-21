package me.kakao.pay.luck.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RecordResponse {
	private String blessTime;
	private long blessAmount;
	private long grappedAmount;
	private int grappedCount;
	private List<LuckyMemberResponse> luckyMembers;
}
