package me.kakao.pay.luck.vo;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlessRequest {
	@Min(1)
	private long amount;
	@Min(1)
	private int maxReceiverCount;
}
