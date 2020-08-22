package me.kakao.pay.common.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {
	private List<String> errors;
}
