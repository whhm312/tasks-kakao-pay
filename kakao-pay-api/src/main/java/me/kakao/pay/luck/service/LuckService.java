package me.kakao.pay.luck.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.kakao.pay.common.domain.Luck;
import me.kakao.pay.common.domain.LuckDetail;
import me.kakao.pay.common.domain.LuckGrabRecord;
import me.kakao.pay.common.domain.LuckRecord;
import me.kakao.pay.common.domain.LuckyMember;
import me.kakao.pay.common.exception.AlreadyGrabUserException;
import me.kakao.pay.common.exception.BlesserNotAllowGrabException;
import me.kakao.pay.common.exception.ExpiredLuckException;
import me.kakao.pay.common.exception.FailedCreateTokenException;
import me.kakao.pay.common.exception.FailedInsertLuckDetailException;
import me.kakao.pay.common.exception.FailedInsertLuckException;
import me.kakao.pay.common.exception.FullGrabException;
import me.kakao.pay.common.exception.InvalidTokenException;
import me.kakao.pay.common.exception.NotValidMemberException;
import me.kakao.pay.luck.dao.LuckDAO;

@Service
public class LuckService {
	@Value("${token.generate.retry.count}")
	private int RETRY_COUNT;

	@Value("${token.expired.minute}")
	private int EXPIRED_MINUTE;

	@Value("${token.expired.days}")
	private int EXPIRED_DAYS;

	private LuckDAO luckDAO;

	public LuckService(LuckDAO luckDAO) {
		this.luckDAO = luckDAO;
	}

	public String blessing(Luck luck) {
		luck.setExpiredDays(EXPIRED_DAYS);
		
		for (int i = 0; i < RETRY_COUNT; i++) {

			String token = TokenGenerator.get();
			if (isUniqueToken(luck)) {
				luck.setToken(token);

				blessLuck(luck);

				return token;
			}
		}

		throw new FailedCreateTokenException(RETRY_COUNT + " times tried to create token but it's failed.");
	}

	@Transactional
	private void blessLuck(Luck luck) {
		luckDAO.insert(luck);

		int luckSeq = luck.getSeq();
		if (luckSeq < 1) {
			throw new FailedInsertLuckException("Failed to insert luck. {" + luck.toString() + "}");
		}

		LuckDetail luckDetail = null;
		List<Long> dividedAmounts = AmountDivideService.divide(luck.getAmount(), luck.getMaxGrabberCount());
		for (Long amount : dividedAmounts) {
			luckDetail = new LuckDetail();
			luckDetail.setLuckSeq(luckSeq);
			luckDetail.setAmount(amount);
			int luckDetailSeq = luckDAO.insert(luckDetail);
			if (luckDetailSeq < 1) {
				throw new FailedInsertLuckDetailException("Failed to insert luck detail. {" + luckDetail.toString() + "}");
			}
		}

	}

	public long grab(String token, String userId, String roomId) {
		Luck luck = validateGrab(token, userId, roomId);

		List<LuckDetail> luckDetails = luckDAO.selectVaildGrabLuckDetail(luck);

		LuckGrabRecord record = new LuckGrabRecord();
		record.setGrabUserId(userId);
		record.setLuckDetailSeq(luckDetails.get(0).getSeq());
		record.setLuckSeq(luckDetails.get(0).getLuckSeq());

		luckDAO.insert(record);

		return luckDetails.get(0).getAmount();
	}

	public LuckRecord getLuckRecords(Luck luck) {
		if (isOverDate(luck)) {
			throw new ExpiredLuckException("The luck is already expired date. {" + EXPIRED_DAYS + "days.}");
		}

		LuckRecord luckRecords = luckDAO.selectLuckRecord(luck);
		if (luckRecords == null) {
			throw new InvalidTokenException(
					"Cannot find the luck. {X-USER-ID : " + luck.getBlesserId() + ", X-ROOM-ID : " + luck.getRoomId() + ", token : " + luck.getToken() + "}");
		}

		List<LuckyMember> luckyMembers = luckDAO.getLuckyMembers(luckRecords.getSeq());
		if (luckyMembers == null) {
			luckRecords.setLuckyMembers(new ArrayList<LuckyMember>());
		} else {
			luckRecords.setLuckyMembers(luckyMembers);
		}

		return luckRecords;
	}

	private Luck validateGrab(String token, String userId, String roomId) {
		Luck condition = new Luck();
		condition.setToken(token);
		condition.setRoomId(roomId);
		if (isOverDate(condition)) {
			throw new ExpiredLuckException("The luck is already expired date. {" + EXPIRED_DAYS + "days.}");
		}

		if (isOverTime(condition)) {
			throw new ExpiredLuckException("The luck is expired time. {" + EXPIRED_MINUTE + "min.}");
		}

		Luck luck = luckDAO.selectLuck(condition);

		if (luck == null) {
			throw new InvalidTokenException("Cannot find the luck by the token .{ " + token + "}");
		}

		if (isNotValidMember(luck.getRoomId(), roomId)) {
			throw new NotValidMemberException(userId + " is not the member of the chat.");
		}

		if (isBlesser(luck, userId)) {
			throw new BlesserNotAllowGrabException("Blesser is not allow to grab blesser's luck.");
		}

		int luckSeq = luck.getSeq();

		if (isDuplicatedGrab(luckSeq, userId)) {
			throw new AlreadyGrabUserException(userId + " is already grab the luck.");
		}

		if (isFullGrab(luck)) {
			throw new FullGrabException("The luck is over to grab.");
		}

		return luck;
	}

	public boolean isUniqueToken(Luck luck) {
		return luckDAO.countSameToken(luck) < 1;
	}

	public boolean isNotValidMember(String luckRoomId, String roomId) {
		return !luckRoomId.equals(roomId);
	}

	public boolean isDuplicatedGrab(int seq, String userId) {
		LuckGrabRecord record = new LuckGrabRecord();
		record.setLuckSeq(seq);
		record.setGrabUserId(userId);
		record = luckDAO.selectLuckGrabRecord(record);
		if (record == null) {
			return false;
		}
		return record.getLuckDetailSeq() > 0;
	}

	public boolean isFullGrab(Luck luck) {
		return luckDAO.countGrabRecord(luck.getSeq()) >= luck.getMaxGrabberCount();
	}

	public boolean isBlesser(Luck luck, String userId) {
		luck.setBlesserId(userId);
		Luck selectLuck = luckDAO.selectLuck(luck);
		if (selectLuck == null) {
			return false;
		}
		return selectLuck.getSeq() > 0;
	}

	public boolean isOverTime(Luck luck) {
		luck.setExpiredMinutes(EXPIRED_MINUTE);
		luck.setExpiredDays(0);
		return luckDAO.isVaildDateTimeLuck(luck);
	}

	public boolean isOverDate(Luck luck) {
		luck.setExpiredDays(EXPIRED_DAYS);
		luck.setExpiredMinutes(0);
		return luckDAO.isVaildDateTimeLuck(luck);
	}
}
