package me.kakao.pay.common.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Luck {
	private long amount;
	private int maxGrabberCount;
	private String token;
	private String roomId;
	private String blesserId;
}
