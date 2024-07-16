package kr.spring.goods.service;

import kr.spring.goods.service.PortOneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PortOneServiceImpl implements PortOneService {
	
	 private final RestTemplate restTemplate;

	    @Autowired
	    public PortOneServiceImpl(RestTemplate restTemplate) {
	        this.restTemplate = restTemplate;
	    }
    @Value("${import.imp_key}")
    private String apiKey;

    @Value("${import.imp_secret}")
    private String apiSecret;

    @Override
    public Map<String, Object> requestPayment(String merchantUid, int amount, String cardNumber, String expiry, String birth, String pwd2digit) {
        String customerUid = "customer_uid"; // 고객 식별자 (고유한 값으로 설정)

        // 고객 등록
        Map<String, Object> customerResponse = registerCustomer(customerUid, cardNumber, expiry, birth, pwd2digit);
       

        String token = getAccessToken();
        String url = "https://api.iamport.kr/subscribe/payments/again";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, Object> body = new HashMap<>();
        body.put("merchant_uid", merchantUid);
        body.put("amount", amount);
        body.put("customer_uid", customerUid);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
       

        return response.getBody();
    }

    @Override
    public Map<String, Object> registerCustomer(String customerUid, String cardNumber, String expiry, String birth, String pwd2digit) {
        String token = getAccessToken();
        String url = "https://api.iamport.kr/subscribe/customers/" + customerUid;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, Object> body = new HashMap<>();
        body.put("card_number", cardNumber);
        body.put("expiry", expiry);
        body.put("birth", birth);
        body.put("pwd_2digit", pwd2digit);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        return response.getBody();
    }

    private String getAccessToken() {
        String url = "https://api.iamport.kr/users/getToken";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("imp_key", apiKey);
        body.put("imp_secret", apiSecret);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        Map responseBody = (Map) response.getBody().get("response");
        return (String) responseBody.get("access_token");ㄴ
    }

    @Override
    public Map<String, Object> requestRefund(String impUid, int amount, String reason) {
        String token = getAccessToken();
        String url = "https://api.iamport.kr/payments/cancel";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, Object> body = new HashMap<>();
        body.put("imp_uid", impUid);
        body.put("amount", amount);
        body.put("reason", reason);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);


        return response.getBody();
    }
}
