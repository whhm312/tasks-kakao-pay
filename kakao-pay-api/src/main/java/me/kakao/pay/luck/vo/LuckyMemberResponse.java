package me.kakao.pay.luck.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LuckyMemberResponse {
	private String userId;
	private long amount;
}
