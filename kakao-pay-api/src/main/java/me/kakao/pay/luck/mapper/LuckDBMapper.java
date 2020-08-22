package me.kakao.pay.luck.mapper;

import org.apache.ibatis.annotations.Mapper;

import me.kakao.pay.common.domain.Luck;

@Mapper
public interface LuckDBMapper {
	public String selectNow();

	public int insertLuck(Luck blessing);

	public int countSameToken(Luck blessing);
}
