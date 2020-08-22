package me.kakao.pay.common.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotValiedResponse extends ErrorResponse {
	private List<String> details;
}
