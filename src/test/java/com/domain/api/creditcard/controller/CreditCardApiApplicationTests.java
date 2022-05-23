package com.domain.api.creditcard.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.domain.api.creditcard.Application;
import com.domain.api.creditcard.bean.CreditCardBean;
import com.domain.api.creditcard.repository.impl.CreditCardRepositoryImpl;

//import junit.framework.TestCase;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = { "classpath:application.properties" })
@DirtiesContext(classMode= ClassMode.AFTER_CLASS)
//@ActiveProfiles("test")
public class CreditCardApiApplicationTests {//extends TestCase {

	@LocalServerPort
	private int port;

	@Mock
	private MongoTemplate mongoTemplate;

	@Autowired
	private CreditCardRepositoryImpl repository;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@BeforeClass
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(repository, "mongoTemplate", mongoTemplate);
	}

	@Test
	public void testGetAllCreditCards_HappyScneario() {
		ReflectionTestUtils.setField(repository, "mongoTemplate", mongoTemplate);
		Mockito.when(mongoTemplate.findAll(CreditCardBean.class)).thenReturn(getCreditCards());
		ResponseEntity<ArrayList> response = when_ICall_GetAllCreditCards();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	// Credit Card Number is INVALID
	@Test
	public void testCreateCreditCard_Invalid_CreditCards_Number_Scneario_1() {
		ReflectionTestUtils.setField(repository, "mongoTemplate", mongoTemplate);
		// Mockito.when(mongoTemplate.findAll(CreditCardBean.class)).thenReturn(getCreditCards());
		final String requestData = "{\"name\": \"Ankit Sharma\",\"cardNumber\": \"INVALID\",\"balance\": \"0\",\"limit\": \"2000\"}";
		ResponseEntity<ArrayList> response = when_ICall_CreateCreditCard(requestData);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	// Credit Card Number id less than 13 digit
	@Test
	public void testCreateCreditCard_Invalid_CreditCards_Number_Scneario_2() {
		ReflectionTestUtils.setField(repository, "mongoTemplate", mongoTemplate);
		// Mockito.when(mongoTemplate.findAll(CreditCardBean.class)).thenReturn(getCreditCards());
		final String requestData = "{\"name\": \"Ankit Sharma\",\"cardNumber\": \"123456789012\",\"balance\": \"0\",\"limit\": \"2000\"}";
		ResponseEntity<ArrayList> response = when_ICall_CreateCreditCard(requestData);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	// Credit Card Number id greater than 19 digit
	@Test
	public void testCreateCreditCard_Invalid_CreditCards_Number_Scneario_3() {
		ReflectionTestUtils.setField(repository, "mongoTemplate", mongoTemplate);
		// Mockito.when(mongoTemplate.findAll(CreditCardBean.class)).thenReturn(getCreditCards());
		final String requestData = "{\"name\": \"Ankit Sharma\",\"cardNumber\": \"12345678901212345678\",\"balance\": \"0\",\"limit\": \"2000\"}";
		ResponseEntity<ArrayList> response = when_ICall_CreateCreditCard(requestData);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	// Credit Card Number id valid but entry already existed in DB
	@Test
	public void testCreateCreditCard_Invalid_CreditCards_Number_Scneario_4() {

		ReflectionTestUtils.setField(repository, "mongoTemplate", mongoTemplate);
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is("4111111111111111"));

		Mockito.when(mongoTemplate.findOne(query, CreditCardBean.class)).thenReturn(getCreditCard());

		final String requestData = "{\"name\": \"Ankit Sharma\",\"cardNumber\": \"4111111111111111\",\"balance\": \"0\",\"limit\": \"2000\"}";

		ResponseEntity<ArrayList> response = when_ICall_CreateCreditCard(requestData);

		assertNotNull(response);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	// Credit Card Number id valid & entry does not exists in DB
	@Test
	public void testCreateCreditCard_Invalid_CreditCards_Number_Scneario_5() {
		ReflectionTestUtils.setField(repository, "mongoTemplate", mongoTemplate);
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is("5555555555554444"));

		Mockito.when(mongoTemplate.findOne(query, CreditCardBean.class)).thenReturn(null);

		Mockito.when(mongoTemplate.save(getCreditCard()));

		final String requestData = "{\"name\": \"Ankit Sharma\",\"cardNumber\": \"5555555555554444\",\"balance\": \"0\",\"limit\": \"2000\"}";

		ResponseEntity<CreditCardBean> response = when_ICall_CreateCreditCardHappyPath(requestData);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	private ResponseEntity<ArrayList> when_ICall_GetAllCreditCards() {

		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		final String url = "http://localhost:" + port + "/creditcard";

		HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);

		ResponseEntity<ArrayList> response = testRestTemplate.exchange(url, HttpMethod.GET, entity, ArrayList.class);

		return response;
	}

	private ResponseEntity<ArrayList> when_ICall_CreateCreditCard(String requestData) {

		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		final String url = "http://localhost:" + port + "/creditcard";

		HttpEntity<String> entity = new HttpEntity<String>(requestData, httpHeaders);

		ResponseEntity<ArrayList> response = testRestTemplate.exchange(url, HttpMethod.POST, entity, ArrayList.class);

		return response;
	}

	private ResponseEntity<CreditCardBean> when_ICall_CreateCreditCardHappyPath(String requestData) {

		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		final String url = "http://localhost:" + port + "/creditcard";

		HttpEntity<String> entity = new HttpEntity<String>(requestData, httpHeaders);

		ResponseEntity<CreditCardBean> response = testRestTemplate.exchange(url, HttpMethod.POST, entity,
				CreditCardBean.class);

		return response;
	}

	public static List<CreditCardBean> getCreditCards() {

		List<CreditCardBean> creditCards = new ArrayList<>();

		CreditCardBean bean1 = new CreditCardBean();
		bean1.setBalance("0");
		bean1.setCardNumber("1111222233334444");
		bean1.setLimit("2000");
		bean1.setName("Name1");

		CreditCardBean bean2 = new CreditCardBean();
		bean2.setBalance("0");
		bean2.setCardNumber("1111222233334442");
		bean2.setLimit("2000");
		bean2.setName("Name2");

		creditCards.add(bean1);
		creditCards.add(bean2);

		return creditCards;

	}

	public static CreditCardBean getCreditCard() {

		// List<CreditCardBean> creditCards = new ArrayList<>();

		CreditCardBean bean1 = new CreditCardBean();
		bean1.setBalance("0");
		bean1.setCardNumber("4111111111111111");
		bean1.setLimit("2000");
		bean1.setName("Name1");

		return bean1;

	}

}
