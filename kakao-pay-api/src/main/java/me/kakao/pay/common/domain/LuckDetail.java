package me.kakao.pay.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LuckDetail {
	private int seq;
	private int luckSeq;
	private long amount;
}
