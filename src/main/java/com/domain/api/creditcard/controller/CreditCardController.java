package com.domain.api.creditcard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.domain.api.creditcard.bean.CreditCardBean;
import com.domain.api.creditcard.service.CreditCardService;

@RestController
public class CreditCardController {
	
	@Autowired
	private CreditCardService service;
	
	@GetMapping(value = "creditcard", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CreditCardBean>> getAllCreditCards() {
		List<CreditCardBean> creditCards= service.getAllCreditCards();
		return new ResponseEntity<List<CreditCardBean>>(creditCards, HttpStatus.OK);
	}
	
	@GetMapping(value = "/creditcard/{creditCardNumber}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreditCardBean> getCreditCardById(@PathVariable String creditCardNumber) {
		CreditCardBean creditCard= service.getCreditCardById(creditCardNumber);
		return new ResponseEntity<CreditCardBean>(creditCard, HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/creditcard", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreditCardBean> addNewCreditCard(@RequestBody CreditCardBean creditCard) {
		CreditCardBean creditCardResponse= service.addNewCreditCard(creditCard);
		return new ResponseEntity<CreditCardBean>(creditCardResponse, HttpStatus.CREATED);
	}
}