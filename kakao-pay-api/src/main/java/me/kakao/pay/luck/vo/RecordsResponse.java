package me.kakao.pay.luck.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RecordsResponse {
	private int totalCount;
	private List<RecordResponse> records;
}
