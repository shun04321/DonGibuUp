package kr.spring.goods.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import kr.spring.goods.dao.PaymentMapper;
import kr.spring.goods.vo.PaymentVO;

import java.util.HashMap;
import java.util.Map;

@Service
public class PortOneServiceImpl implements PortOneService {

    @Value("${portone.api_key}")
    private String apiKey;

    @Value("${portone.api_secret}")
    private String apiSecret;

    @Value("${portone.customer_code}")
    private String customerCode;

    private final RestTemplate restTemplate;

    @Autowired
    private PaymentMapper paymentMapper;

    public PortOneServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getAccessToken() {
        String url = "https://api.iamport.kr/users/getToken";

        Map<String, String> body = new HashMap<>();
        body.put("imp_key", apiKey);
        body.put("imp_secret", apiSecret);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, body, Map.class);
        Map responseBody = response.getBody();

        if (responseBody != null && responseBody.get("response") != null) {
            Map responseData = (Map) responseBody.get("response");
            return (String) responseData.get("access_token");
        } else {
            throw new RuntimeException("Failed to get access token");
        }
    }

    @Override
    public Map<String, Object> requestPayment(String merchantUid, int amount, String cardNumber, String expiry, String birth, String pwd2digit) {
        String url = "https://api.iamport.kr/subscribe/payments/onetime";

        String token = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        
        Map<String, Object> body = new HashMap<>();
        body.put("merchant_uid", merchantUid);
        body.put("amount", amount);
        body.put("card_number", cardNumber);
        body.put("expiry", expiry);
        body.put("birth", birth);
        body.put("pwd_2digit", pwd2digit);
        body.put("customer_uid", customerCode);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        Map<String, Object> responseBody = response.getBody();

        // 결제 정보 저장
        PaymentVO paymentVO = new PaymentVO();
        paymentVO.setMerchantUid(merchantUid);
        paymentVO.setAmount(amount);
        paymentVO.setStatus(responseBody != null && responseBody.get("status") != null ? (String) responseBody.get("status") : "failed");
        paymentVO.setCardNumber(cardNumber);
        paymentVO.setExpiry(expiry);
        paymentVO.setBirth(birth);
        paymentVO.setPwd2digit(pwd2digit);

        paymentMapper.insertPaymentInfo(paymentVO);
        
        return responseBody;
    }

    @Override
    public Map<String, Object> requestRefund(String impUid, int amount, String reason) {
        String url = "https://api.iamport.kr/payments/cancel";

        String token = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        
        Map<String, Object> body = new HashMap<>();
        body.put("imp_uid", impUid);
        body.put("amount", amount);
        body.put("reason", reason);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        
        return response.getBody();
    }

    @Override
    public void savePaymentInfo(PaymentVO paymentVO) {
        paymentMapper.insertPaymentInfo(paymentVO);
    }
}
