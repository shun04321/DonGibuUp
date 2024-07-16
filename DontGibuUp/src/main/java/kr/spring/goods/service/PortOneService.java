package kr.spring.goods.service;

import java.util.Map;

import kr.spring.goods.vo.PaymentVO;

public interface PortOneService {
    String getAccessToken();
    Map<String, Object> requestPayment(String merchantUid, int amount, String cardNumber, String expiry, String birth, String pwd2digit);
    Map<String, Object> requestRefund(String impUid, int amount, String reason);
    void savePaymentInfo(PaymentVO paymentVO);
}