package me.kakao.pay.common.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ErrorResponse {
	private String code;
	private String message;
}
