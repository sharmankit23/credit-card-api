/**
 * 
 */
package com.domain.api.creditcard.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.domain.api.creditcard.bean.CreditCardBean;
import com.domain.api.creditcard.repository.CreditCardRepository;
import com.domain.api.creditcard.utility.APIException;
import com.domain.api.creditcard.utility.Message;

/**
 * @author sharmankit23
 *
 */
@Repository
public class CreditCardRepositoryImpl implements CreditCardRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<CreditCardBean> getAllCreditCards() {
		return mongoTemplate.findAll(CreditCardBean.class);
	}

	@Override
	public CreditCardBean getCreditCardById(String creditCardNumber) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(creditCardNumber));
		CreditCardBean existingData= mongoTemplate.findOne(query, CreditCardBean.class);
		if(null == existingData) {
			throw new APIException(Message.create("NO_DATA_FOUND", "No data found"), HttpStatus.NOT_FOUND);
		}
		return existingData;
	}

	@Override
	public CreditCardBean addNewCreditCard(CreditCardBean creditCard) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(creditCard.getCardNumber()));
		CreditCardBean existingData= mongoTemplate.findOne(query, CreditCardBean.class);
		if(null != existingData) {
			throw new APIException(Message.create("DUPLICATE_ENTRY", "Duplicate data"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		mongoTemplate.save(creditCard);
		return creditCard;
	}

}
