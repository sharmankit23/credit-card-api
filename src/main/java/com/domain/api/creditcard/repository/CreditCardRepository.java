package com.domain.api.creditcard.repository;

import java.util.List;

import com.domain.api.creditcard.bean.CreditCardBean;

public interface CreditCardRepository {
	
	public List<CreditCardBean> getAllCreditCards();

	public CreditCardBean getCreditCardById(String creditCardNumber);

	public CreditCardBean addNewCreditCard(CreditCardBean creditCard);
}
