package me.kakao.pay.luck.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import me.kakao.pay.common.domain.Blessing;
import me.kakao.pay.common.exception.DuplicatedTokenException;
import me.kakao.pay.luck.dao.LuckDAO;

@Service
public class LuckService {
	@Value("${token.generate.retry.count}")
	private int RETRY_COUNT = 0;

	private LuckDAO luckDAO;

	public LuckService(LuckDAO luckDAO) {
		this.luckDAO = luckDAO;
	}

	public String blessing(Blessing blessing) {
		for (int i = 0; i < RETRY_COUNT; i++) {
			blessing.setToken(TokenGenerator.get());
			if (isUniqueToken(blessing)) {
				luckDAO.insert(blessing);
				return blessing.getToken();
			}
		}
		throw new DuplicatedTokenException();
	}

	public boolean isUniqueToken(Blessing blessing) {
		int count = luckDAO.countSameToken(blessing);
		return count < 1;
	}

}
