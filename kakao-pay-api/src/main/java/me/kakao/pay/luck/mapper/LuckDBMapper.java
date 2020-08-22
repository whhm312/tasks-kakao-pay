package me.kakao.pay.luck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import me.kakao.pay.common.domain.Luck;
import me.kakao.pay.common.domain.LuckDetail;

@Mapper
public interface LuckDBMapper {
	public String selectNow();

	public int insertLuck(Luck luck);

	public int countSameToken(Luck luck);

	public int insertLuckDetail(LuckDetail luckDetail);

	public Luck selectLuck(Luck luck);

	public List<LuckDetail> selectLuckDetail(Luck luck);
}
