package me.kakao.pay.luck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import me.kakao.pay.luck.vo.BlessLuckRequest;
import me.kakao.pay.luck.vo.BlessLuckResponse;
import me.kakao.pay.luck.vo.GrabLuckResponse;
import me.kakao.pay.luck.vo.LuckRecordsResponse;
import me.kakao.pay.luck.vo.LuckyMemberResponse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LuckControllerTest {
	@LocalServerPort
	private int PORT;

	@Autowired
	private TestRestTemplate template;

	private HttpHeaders headers;
	private static String TOKEN;
	private static long GRABBED_AMOUNT;
	private String ROOM_ID = "test_room";
	private long BLESSING_AMOUNT = 10000;
	private int MAX_GRABBER_COUNT = 3;

	@Test
	@Order(1)
	public void testBless() throws URISyntaxException {

		headers = new HttpHeaders();
		headers.add("X-USER-ID", "test_user");
		headers.add("X-ROOM-ID", ROOM_ID);

		URI uri = new URI("http://localhost:" + PORT + "/lucks/bless");

		BlessLuckRequest request = new BlessLuckRequest();
		request.setAmount(BLESSING_AMOUNT);
		request.setMaxGrabberCount(MAX_GRABBER_COUNT);

		ResponseEntity<BlessLuckResponse> response = template.exchange(uri, HttpMethod.POST, new HttpEntity<>(request, headers), BlessLuckResponse.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue(response.getBody().getToken().length() == 3);

		TOKEN = response.getBody().getToken();
	}

	@Test
	@Order(2)
	public void testGrab() throws URISyntaxException {
		assertFalse(StringUtils.isEmpty(TOKEN));

		headers = new HttpHeaders();
		headers.add("X-USER-ID", "test_grap_user");
		headers.add("X-ROOM-ID", ROOM_ID);

		URI uri = new URI("http://localhost:" + PORT + "/lucks/" + TOKEN + "/grab");
		ResponseEntity<GrabLuckResponse> response = template.exchange(uri, HttpMethod.POST, new HttpEntity<>(headers), GrabLuckResponse.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		GRABBED_AMOUNT = response.getBody().getGrabbedAmount();
	}

	@Test
	@Order(3)
	public void testRecords() throws URISyntaxException {
		assertFalse(StringUtils.isEmpty(TOKEN));

		headers = new HttpHeaders();
		headers.add("X-USER-ID", "test_user");
		headers.add("X-ROOM-ID", ROOM_ID);

		URI uri = new URI("http://localhost:" + PORT + "/lucks/" + TOKEN);
		ResponseEntity<LuckRecordsResponse> response = template.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), LuckRecordsResponse.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().getTotalLuckyMemberCount());

		assertEquals(BLESSING_AMOUNT, response.getBody().getBlessAmount());
		assertEquals(1, response.getBody().getTotalLuckyMemberCount());
		assertFalse(StringUtils.isEmpty(response.getBody().getBlessTime()));
		assertTrue(0 < response.getBody().getTotalGrabbedAmount());

		List<LuckyMemberResponse> luckyMembers = response.getBody().getLuckyMembers();
		assertEquals("test_grap_user", luckyMembers.get(0).getUserId());
		assertTrue(0 < luckyMembers.get(0).getAmount());
		assertEquals(GRABBED_AMOUNT, luckyMembers.get(0).getAmount());
	}

	@Test
	@Order(4)
	public void testBedRequestForBless() throws URISyntaxException {

		headers = new HttpHeaders();
		headers.add("X-USER-ID", "test_user");
		headers.add("X-ROOM-ID", ROOM_ID);

		URI uri = new URI("http://localhost:" + PORT + "/lucks/bless");

		BlessLuckRequest request = new BlessLuckRequest();
		request.setAmount(0);
		request.setMaxGrabberCount(0);

		ResponseEntity<BlessLuckResponse> response = template.exchange(uri, HttpMethod.POST, new HttpEntity<>(request, headers), BlessLuckResponse.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

}
