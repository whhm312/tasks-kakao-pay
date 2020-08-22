package me.kakao.pay.luck;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.kakao.pay.common.domain.Blessing;
import me.kakao.pay.luck.mapper.LuckObjectMapper;
import me.kakao.pay.luck.service.LuckService;
import me.kakao.pay.luck.vo.BlessRequest;
import me.kakao.pay.luck.vo.BlessResponse;

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
	public ResponseEntity<BlessResponse> bless(@RequestHeader HttpHeaders headers, @Valid @RequestBody BlessRequest request) {
		Blessing blessing = luckMapper.requestToBlessing(request, getUserId(headers), getRoomId(headers));
		String token = luckService.blessing(blessing);

		BlessResponse response = new BlessResponse();
		response.setToken(token);
		return new ResponseEntity<BlessResponse>(response, HttpStatus.CREATED);
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
