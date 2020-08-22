package me.kakao.pay.luck.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import me.kakao.pay.common.domain.Luck;
import me.kakao.pay.common.exception.DuplicatedTokenException;
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
			luck.setToken(TokenGenerator.get());
			if (isUniqueToken(luck)) {
				luckDAO.insert(luck);
				return luck.getToken();
			}
		}
		throw new DuplicatedTokenException();
	}

	public boolean isUniqueToken(Luck blessing) {
		int count = luckDAO.countSameToken(blessing);
		return count < 1;
	}

}
