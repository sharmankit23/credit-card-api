/**
 * 
 */
package com.domain.api.creditcard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.api.creditcard.bean.CreditCardBean;
import com.domain.api.creditcard.repository.CreditCardRepository;
import com.domain.api.creditcard.service.CreditCardService;
import com.domain.api.creditcard.utility.Validation;

/**
 * @author sharmankit23
 *
 */
@Service
public class CreditCardServiceImpl implements CreditCardService {
	
	@Autowired
	private CreditCardRepository repository;

	@Override
	public List<CreditCardBean> getAllCreditCards() {
		return repository.getAllCreditCards();
	}

	@Override
	public CreditCardBean getCreditCardById(String creditCardNumber) {
		Validation.isCreditCardValid(creditCardNumber);
		return repository.getCreditCardById(creditCardNumber);
	}

	@Override
	public CreditCardBean addNewCreditCard(CreditCardBean creditCard) {
		Validation.isCreditCardValid(creditCard.getCardNumber());
		return repository.addNewCreditCard(creditCard);
	}

}
