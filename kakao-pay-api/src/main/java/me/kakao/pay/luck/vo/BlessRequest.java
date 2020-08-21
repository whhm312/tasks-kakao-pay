package me.kakao.pay.luck.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlessRequest {
	private long amount;
	private int maxReceiverCount;
}
