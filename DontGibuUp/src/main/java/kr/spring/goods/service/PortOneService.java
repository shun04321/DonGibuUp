package kr.spring.goods.service;

import java.util.Map;

public interface PortOneService {
	  Map<String, Object> requestPayment(String merchantUid, int amount, String cardNumber, String expiry, String birth, String pwd2digit);
	    Map<String, Object> requestRefund(String impUid, int amount, String reason);
	    Map<String, Object> registerCustomer(String customerUid, String cardNumber, String expiry, String birth, String pwd2digit);
    
    
}