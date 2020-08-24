package me.kakao.pay.common.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NotValiedResponse extends ErrorResponse {
	private List<String> details;
}
