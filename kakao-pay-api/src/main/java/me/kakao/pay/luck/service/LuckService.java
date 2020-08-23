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
import me.kakao.pay.common.exception.ForbiddenSearchException;
import me.kakao.pay.common.exception.FullGrabException;
import me.kakao.pay.common.exception.InvalidLuckException;
import me.kakao.pay.common.exception.NotValidMemberException;
import me.kakao.pay.luck.dao.LuckDAO;

@Service
public class LuckService {
	@Value("${token.generate.retry.count}")
	private int RETRY_COUNT;
	@Value("${token.expired.minute}")
	private int EXPIRED_MINUTE;

	private LuckDAO luckDAO;

	public LuckService(LuckDAO luckDAO) {
		this.luckDAO = luckDAO;
	}

	public String blessing(Luck luck) {
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

	public boolean isUniqueToken(Luck blessing) {
		int count = luckDAO.countSameToken(blessing);
		return count < 1;
	}

	public long grab(Luck luck, String requestUserId) {
		luck = luckDAO.selectLuck(luck);
		
		if (luck == null) {
			throw new InvalidLuckException("Cannot find the luck.");
		}

		if (isNotValidMember(luck, requestUserId)) {
			throw new NotValidMemberException(requestUserId + " is not the member of the chat.");
		}

		if (isDuplicatedGrab(luck, requestUserId)) {
			throw new AlreadyGrabUserException(requestUserId + " is already grab the luck.");
		}

		if (isBlesser(luck, requestUserId)) {
			throw new BlesserNotAllowGrabException("Blesser is not allow to grab blesser's luck.");
		}

		if (isOverTime(luck)) {
			throw new ExpiredLuckException("The luck is expired time. {" + EXPIRED_MINUTE + "min.}");
		}

		if (isFullGrab(luck)) {
			throw new FullGrabException("The luck is over to grab.");
		}

		List<LuckDetail> luckDetails = luckDAO.selectVaildGrabLuckDetail(luck);

		LuckGrabRecord record = new LuckGrabRecord();
		record.setGrabUserId(requestUserId);
		record.setLuckDetailSeq(luckDetails.get(0).getSeq());
		record.setLuckSeq(luckDetails.get(0).getLuckSeq());

		luckDAO.insert(record);

		return luckDetails.get(0).getAmount();
	}

	// TODO 해당 대화방 인원인지 어떻게 체크할 것인가
	public boolean isNotValidMember(Luck luck, String requestUserId) {
		return false;
	}

	public boolean isDuplicatedGrab(Luck luck, String requestUserId) {
		LuckGrabRecord record = new LuckGrabRecord();
		record.setLuckSeq(luck.getSeq());
		record.setGrabUserId(requestUserId);
		record = luckDAO.selectLuckGrabRecord(record);
		if (record == null) {
			return false;
		}
		return record.getLuckDetailSeq() > 0;
	}

	public boolean isFullGrab(Luck luck) {
		return luckDAO.countGrabRecord(luck.getSeq()) >= luck.getMaxGrabberCount();
	}

	public boolean isBlesser(Luck luck, String requestUserId) {
		luck.setBlesserId(requestUserId);
		Luck selectLuck = luckDAO.selectLuck(luck);
		if (selectLuck == null) {
			return false;
		}
		return selectLuck.getSeq() > 0;
	}

	public boolean isOverTime(Luck luck) {
		return luckDAO.countVaildTimeLuck(luck.getSeq(), EXPIRED_MINUTE) < 1;
	}

	public LuckRecord getLuckRecords(Luck luck) {
		LuckRecord luckRecords = luckDAO.selectLuckRecord(luck);
		if (luckRecords == null) {
			throw new InvalidLuckException("Cannot find the luck.");
		}
		
		if (!luckRecords.getBlesserId().equals(luck.getBlesserId())) {
			throw new ForbiddenSearchException("A luck can search only its owner.");
		}

		if (luckRecords.isExpried()) {
			throw new ExpiredLuckException("The luck is already expired date.");
		}

		List<LuckyMember> luckyMembers = luckDAO.getLuckyMembers(luckRecords.getSeq());
		if (luckyMembers == null) {
			luckRecords.setLuckyMembers(new ArrayList<LuckyMember>());
		} else {
			luckRecords.setLuckyMembers(luckyMembers);
		}
		
		return luckRecords;
	}
}
