package me.kakao.pay.luck.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import me.kakao.pay.common.domain.Luck;
import me.kakao.pay.common.domain.LuckDetail;
import me.kakao.pay.common.domain.LuckGrabRecord;
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

	public Luck selectLuck(Luck luck) {
		return luckDBMapper.selectLuck(luck);
	}

	public List<LuckDetail> selectVaildGrabLuckDetail(Luck luck) {
		return luckDBMapper.selectVaildGrabLuckDetail(luck);
	}

	public long insert(LuckGrabRecord record) {
		return luckDBMapper.insertLuckGrabRecord(record);
	}

	public LuckGrabRecord selectLuckGrabRecord(LuckGrabRecord record) {
		return luckDBMapper.selectLuckGrabRecord(record);
	}

	public int countGrabRecord(int luckSeq) {
		return luckDBMapper.countGrabRecord(luckSeq);
	}

	public int countVaildTimeLuck(int luckSeq, int minutes) {
		return luckDBMapper.countVaildTimeLuck(luckSeq, minutes);
	}

}
