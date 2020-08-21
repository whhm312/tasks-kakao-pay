package me.kakao.pay.luck.dao;

import org.springframework.stereotype.Repository;

import me.kakao.pay.common.domain.Blessing;
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

	public int insert(Blessing blessing) {
		return luckDBMapper.insertBlessing(blessing);
	}

}
