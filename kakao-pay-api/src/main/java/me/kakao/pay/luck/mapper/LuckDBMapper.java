package me.kakao.pay.luck.mapper;

import org.apache.ibatis.annotations.Mapper;

import me.kakao.pay.common.domain.Blessing;

@Mapper
public interface LuckDBMapper {
	public String selectNow();

	public int insertBlessing(Blessing blessing);
}
