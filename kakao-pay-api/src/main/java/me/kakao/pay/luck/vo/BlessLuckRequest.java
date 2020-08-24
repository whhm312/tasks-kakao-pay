package me.kakao.pay.luck.vo;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BlessLuckRequest {
	@Min(1)
	private long amount;
	@Min(1)
	private int maxGrabberCount;
}
