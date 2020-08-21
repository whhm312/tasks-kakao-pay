package me.kakao.pay.common.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Blessing {
	private long amount;
	private int maxReceiverCount;
	private String token;
	private String roomId;
	private String blesserId;
}
