package me.kakao.pay.luck.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class AmountDivideService {

	public static List<Long> divide(long totalAmount, int max) {
		long amount = totalAmount / max;

		Map<Integer, Long> map = new HashMap<>();
		for (int i = 0; i < max; i++) {
			map.put(i, amount);
		}

		int randomKey = (int) (map.size() * Math.random());
		long remainder = totalAmount - (amount * max);
		map.put(randomKey, map.get(randomKey) + remainder);

		return new ArrayList<Long>(map.values());
	}
}
