package kr.spring.subscription.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import kr.spring.subscription.dao.SubscriptionMapper;
import kr.spring.subscription.vo.SubscriptionVO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.google.gson.Gson;
@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {
	@Autowired
	SubscriptionMapper SubscriptionMapper;

	@Override
	public void insertSubscription(SubscriptionVO subscriptionVO) {
		SubscriptionMapper.insertSubscription(subscriptionVO);
	}

	@Override
	public void endSubscription(long sub_num) {
		SubscriptionMapper.endSubscription(sub_num);
		
	}

	@Override
	public SubscriptionVO getSubscription(long sub_num) {
		return SubscriptionMapper.getSubscription(sub_num);
	}

	@Override
	public void deleteSubscription(long sub_num) {
		SubscriptionMapper.deleteSubscription(sub_num);
	}

	@Override
	public long getSub_num() {
		return SubscriptionMapper.getSub_num();
	}

	public String getToken() {
		
		RestTemplate restTemplate = new RestTemplate();
	
		//서버로 요청할 Header
		 HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		
	    
	    Map<String, Object> map = new HashMap<>();
	    map.put("imp_key", "1607808574122853");
	    map.put("imp_secret", "pRNB4gue3y2Y7MvkiLRCrqECvVseUrmFKo1XtMokvRAj5LCOZ9zFLFIOAcYTZQbApYFMPRMoNfo8R5NE");
	    
	   
	    Gson var = new Gson();
	    String json=var.toJson(map);
		//서버로 요청할 Body
	   
	    HttpEntity<String> entity = new HttpEntity<>(json,headers);
		return restTemplate.postForObject("https://api.iamport.kr/users/getToken", entity, String.class);
	}

	@Override
	public String getTodayDateString() {
		  // 현재 날짜 가져오기
        LocalDate today = LocalDate.now();
        
        // 날짜 포맷 지정 (예: "yyyy-MM-dd")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // 포맷에 맞게 날짜를 문자열로 변환하여 반환
        return today.format(formatter);
    }
}

