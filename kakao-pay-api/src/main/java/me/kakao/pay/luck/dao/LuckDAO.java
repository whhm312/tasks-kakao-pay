package me.kakao.pay.luck.dao;

import org.springframework.stereotype.Repository;

import me.kakao.pay.common.domain.Luck;
import me.kakao.pay.common.domain.LuckDetail;
import me.kakao.pay.luck.mapper.LuckDBMapper;

@Repository
public class LuckDAO {
	private LuckDBMapper luckDBMapper;

	public LuckDAO(LuckDBMapper luckDBMapper) {
		this.luckDBMapper = luckDBMapper;
	}

	public String now() {
		return luckDBMapper.selectNow();
	}

	public int insert(Luck luck) {
		return luckDBMapper.insertLuck(luck);
	}

	public int insert(LuckDetail luckDetail) {
		return luckDBMapper.insertLuckDetail(luckDetail);
	}

	public int countSameToken(Luck luck) {
		return luckDBMapper.countSameToken(luck);
	}

}
