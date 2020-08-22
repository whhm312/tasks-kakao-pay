package me.kakao.pay.luck.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.kakao.pay.common.domain.Luck;
import me.kakao.pay.common.domain.LuckDetail;
import me.kakao.pay.common.exception.FailedCreateTokenException;
import me.kakao.pay.common.exception.FailedInsertLuckDetailException;
import me.kakao.pay.common.exception.FailedInsertLuckException;
import me.kakao.pay.luck.dao.LuckDAO;

@Service
public class LuckService {
	@Value("${token.generate.retry.count}")
	private int RETRY_COUNT;

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

}
