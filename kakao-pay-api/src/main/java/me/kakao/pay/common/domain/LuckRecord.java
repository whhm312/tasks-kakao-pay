package me.kakao.pay.common.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LuckRecord extends Luck {
	private boolean isExpried;
	private int totalGrabbedAmount;
	private int totalLuckyMemberCount;
	private List<LuckyMember> luckyMembers;
}
