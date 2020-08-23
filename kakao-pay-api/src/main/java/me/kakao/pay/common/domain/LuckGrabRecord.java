package me.kakao.pay.common.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LuckGrabRecord {
	private int seq;
	private int luckSeq;
	private int luckDetailSeq;
	private String grabUserId;
	private Date grabDate;
}
