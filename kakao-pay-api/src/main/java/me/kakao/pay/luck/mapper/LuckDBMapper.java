package me.kakao.pay.luck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import me.kakao.pay.common.domain.Luck;
import me.kakao.pay.common.domain.LuckDetail;
import me.kakao.pay.common.domain.LuckGrabRecord;
import me.kakao.pay.common.domain.LuckRecord;
import me.kakao.pay.common.domain.LuckyMember;

@Mapper
public interface LuckDBMapper {
	public String selectNow();

	public int insertLuck(Luck luck);

	public int countSameToken(Luck luck);

	public int insertLuckDetail(LuckDetail luckDetail);

	public Luck selectLuck(Luck luck);

	public List<LuckDetail> selectLuckDetail(Luck luck);

	public List<LuckDetail> selectVaildGrabLuckDetail(Luck luck);

	public long insertLuckGrabRecord(LuckGrabRecord record);

	public LuckGrabRecord selectLuckGrabRecord(LuckGrabRecord record);

	public int countGrabRecord(int luckSeq);

	public boolean isVaildDateTimeLuck(Luck luck);

	public LuckRecord selectLuckRecord(Luck luck);

	public List<LuckyMember> selectLuckGrabRecordBySeq(int luckSeq);

	public Luck selectLuckByToken(String token);

}
