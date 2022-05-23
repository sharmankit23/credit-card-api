package com.domain.api.creditcard.controller;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

//@Profile("test")
//@Configuration
public class MongoDBTemplateMockConfig {
	
	 //@Bean
	    //@Primary
	    public MongoTemplate mongoTemplate() {
	        return Mockito.mock(MongoTemplate.class);
	    }

}
