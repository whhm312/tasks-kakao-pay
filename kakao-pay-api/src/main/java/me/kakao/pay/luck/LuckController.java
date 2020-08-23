package me.kakao.pay.luck;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.kakao.pay.common.domain.Luck;
import me.kakao.pay.luck.mapper.LuckObjectMapper;
import me.kakao.pay.luck.service.LuckService;
import me.kakao.pay.luck.vo.BlessLuckRequest;
import me.kakao.pay.luck.vo.BlessLuckResponse;
import me.kakao.pay.luck.vo.GrabLuckResponse;

@RestController
@RequestMapping("/lucks")
public class LuckController {
	private LuckObjectMapper luckMapper;
	private LuckService luckService;

	public LuckController(LuckService luckService, LuckObjectMapper luckMapper) {
		this.luckService = luckService;
		this.luckMapper = luckMapper;
	}

	@Value("${requestheaders.user.id}")
	private String HEAD_USER_ID;

	@Value("${requestheaders.room.id}")
	private String HEAD_ROOM_ID;

	@PostMapping("/bless")
	public ResponseEntity<BlessLuckResponse> bless(@RequestHeader HttpHeaders headers, @Valid @RequestBody BlessLuckRequest request) {
		Luck luck = luckMapper.requestToBlessing(request, getUserId(headers), getRoomId(headers));
		String token = luckService.blessing(luck);

		BlessLuckResponse response = new BlessLuckResponse();
		response.setToken(token);
		return new ResponseEntity<BlessLuckResponse>(response, HttpStatus.CREATED);
	}

	@PostMapping("/grab/{token}")
	public ResponseEntity<GrabLuckResponse> grab(@RequestHeader HttpHeaders headers, @PathVariable(name = "token", required = true) String token) {
		Luck luck = luckMapper.requestToGrab(token, getRoomId(headers));
		long grabbedAmount = luckService.grab(luck, getUserId(headers));

		GrabLuckResponse response = new GrabLuckResponse();
		response.setGrabbedAmount(grabbedAmount);
		return new ResponseEntity<GrabLuckResponse>(response, HttpStatus.CREATED);
	}
	
	private String getUserId(HttpHeaders headers) {
		if (headers.get(HEAD_USER_ID) == null) {
			return "";
		}

		return headers.get(HEAD_USER_ID).get(0);
	}

	private String getRoomId(HttpHeaders headers) {
		if (headers.get(HEAD_ROOM_ID) == null) {
			return "";
		}

		return headers.get(HEAD_ROOM_ID).get(0);
	}

}
