package com.domain.api.creditcard.service;

import java.util.List;

import com.domain.api.creditcard.bean.CreditCardBean;

public interface CreditCardService {

	public List<CreditCardBean> getAllCreditCards();

	public CreditCardBean getCreditCardById(String creditCardNumber);

	public CreditCardBean addNewCreditCard(CreditCardBean creditCard);

}
