package com.domain.api.creditcard.utility;

import org.springframework.http.HttpStatus;

public class Validation {
	
	private static String REGEX = "\\d+";
	
	public static void isCreditCardValid(String ccNumber) {
		
		if(!ccNumber.matches(REGEX) || ccNumber.length() < 13 || ccNumber.length()>19 || !mod10Check(ccNumber)) {
			throw new APIException(Message.create("BAD_REQUEST", "Credit Card Number Not Valid"), HttpStatus.BAD_REQUEST);	
		}
   }

	public static boolean mod10Check(String ccNumber) {
		int sum = 0;
		boolean alternate = false;
		for (int i = ccNumber.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(ccNumber.substring(i, i + 1));
			if (alternate) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			alternate = !alternate;
		}
		return (sum % 10 == 0);
	}

}
