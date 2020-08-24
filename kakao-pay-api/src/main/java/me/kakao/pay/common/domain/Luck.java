package me.kakao.pay.common.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Luck {
	private int seq;
	private String blesserId;
	private String roomId;
	private long amount;
	private int maxGrabberCount;
	private String token;
	private Date blessingDate;
	private int expiredDays;
	private int expiredMinutes;
}
