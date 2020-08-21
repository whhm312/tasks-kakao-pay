package me.kakao.pay.luck.service;

import org.springframework.stereotype.Service;

import me.kakao.pay.common.domain.Blessing;
import me.kakao.pay.luck.dao.LuckDAO;

@Service
public class LuckService {
	private LuckDAO luckDAO;

	public String blessing(Blessing blessing) {
		String token = TokenGenerator.get(3);
		blessing.setToken(token);

		luckDAO.insert(blessing);

		return token;
	}

}
