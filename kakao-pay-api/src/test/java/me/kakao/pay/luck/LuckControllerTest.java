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
import org.junit.jupiter.api.TestInstance;
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

import me.kakao.pay.luck.vo.BlessRequest;
import me.kakao.pay.luck.vo.BlessResponse;
import me.kakao.pay.luck.vo.GrapResponse;
import me.kakao.pay.luck.vo.LuckyMemberResponse;
import me.kakao.pay.luck.vo.RecordResponse;
import me.kakao.pay.luck.vo.RecordsResponse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LuckControllerTest {
	@LocalServerPort
	private int PORT;

	@Autowired
	private TestRestTemplate template;

	private HttpHeaders headers;
	private String TOKEN;
	private String ROOM_ID = "test_room";
	private long BLESSING_AMOUNT = 10000;
	private int MAX_RECEIVER_COUNT = 3;

	@Test
	@Order(1)
	public void testGive() throws URISyntaxException {

		headers = new HttpHeaders();
		headers.add("X-USER-ID", "test_user");
		headers.add("X-ROOM-ID", ROOM_ID);

		URI uri = new URI("http://localhost:" + PORT + "/lucks/give");

		BlessRequest request = new BlessRequest();
		request.setAmount(BLESSING_AMOUNT);
		request.setMaxReceiverCount(MAX_RECEIVER_COUNT);

		ResponseEntity<BlessResponse> response = template.exchange(uri, HttpMethod.POST, new HttpEntity<>(request, headers), BlessResponse.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue(response.getBody().getToken().length() == 3);

		TOKEN = response.getBody().getToken();
	}

	@Test
	@Order(2)
	public void testGrap() throws URISyntaxException {
		assertFalse(StringUtils.isEmpty(TOKEN));

		headers = new HttpHeaders();
		headers.add("X-USER-ID", "test_grap_user");
		headers.add("X-ROOM-ID", ROOM_ID);

		URI uri = new URI("http://localhost:" + PORT + "/lucks/" + TOKEN + "/grap");
		ResponseEntity<GrapResponse> response = template.exchange(uri, HttpMethod.POST, new HttpEntity<>(headers), GrapResponse.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	@Order(3)
	public void testRecords() throws URISyntaxException {
		assertFalse(StringUtils.isEmpty(TOKEN));

		headers = new HttpHeaders();
		headers.add("X-USER-ID", "test_user");
		headers.add("X-ROOM-ID", ROOM_ID);

		URI uri = new URI("http://localhost:" + PORT + "/lucks/" + TOKEN + "/records");
		ResponseEntity<RecordsResponse> response = template.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), RecordsResponse.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().getTotalCount());

		List<RecordResponse> records = response.getBody().getRecords();
		assertEquals(BLESSING_AMOUNT, records.get(0).getBlessAmount());
		assertEquals(1, records.get(0).getGrappedCount());
		assertFalse(StringUtils.isEmpty(records.get(0).getBlessTime()));
		assertTrue(0L < records.get(0).getGrappedAmount());

		List<LuckyMemberResponse> luckyMembers = records.get(0).getLuckyMembers();
		String expected = "김혜민";
		assertEquals(expected, luckyMembers.get(0).getName());
		assertTrue(0L < luckyMembers.get(0).getBlessAmount());
	}

}