package kr.spring.subscription.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import kr.spring.category.dao.CategoryMapper;
import kr.spring.category.vo.DonationCategoryVO;
import kr.spring.subscription.dao.SubscriptionMapper;
import kr.spring.subscription.vo.SubscriptionVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionMapper subscriptionMapper;
    
    @Autowired
    private CategoryMapper categoryMapper;

    private static final String IMP_KEY = "1607808574122853";
    private static final String IMP_SECRET = "pRNB4gue3y2Y7MvkiLRCrqECvVseUrmFKo1XtMokvRAj5LCOZ9zFLFIOAcYTZQbApYFMPRMoNfo8R5NE";

   
    public String getToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> map = new HashMap<>();
        map.put("imp_key", IMP_KEY);
        map.put("imp_secret", IMP_SECRET);

        String json = new Gson().toJson(map);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        return restTemplate.postForObject("https://api.iamport.kr/users/getToken", entity, String.class);
    }

    @Override
    public long getSub_num() {
        return subscriptionMapper.getSub_num();
    }

    @Override
    public void insertSubscription(SubscriptionVO subscriptionVO) {
        subscriptionMapper.insertSubscription(subscriptionVO);
    }

    @Override
    public void endSubscription(long sub_num) {
        subscriptionMapper.endSubscription(sub_num);
    }

    @Override
    public SubscriptionVO getSubscription(long sub_num) {
    	SubscriptionVO subscription = subscriptionMapper.getSubscription(sub_num);
    	  if (subscription != null) {
			  String regDate = subscription.getReg_date(); // reg_date는 문자열로 가정
			  subscription.setReg_date(regDate); // 변환된 Date 객체를 설정
          }
        return subscription;
    }

    @Override
    public void deleteSubscription(long sub_num) {
        subscriptionMapper.deleteSubscription(sub_num);
    }

    @Override
    public int getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.KOREA);
        return Integer.parseInt(sdf.format(new Date()));
    }

	@Override
	public List<SubscriptionVO> getSubscriptionByDay(int today) {
		return subscriptionMapper.getSubscriptionByDay(today);
	}

	@Override
	public void updateSub_status(long sub_num) {
		subscriptionMapper.updateSub_status(sub_num);
	}

	 public List<SubscriptionVO> getSubscriptionByMem_numWithCategories(long mem_num) {
	        List<SubscriptionVO> subscriptions = subscriptionMapper.getSubscriptionByMem_num(mem_num);

	        for (SubscriptionVO subscription : subscriptions) {
	            DonationCategoryVO donationCategory = categoryMapper.selectDonationCategory(subscription.getDcate_num());
	            subscription.setDonationCategory(donationCategory); // SubscriptionVO에 DonationCategoryVO 필드를 추가해야 함
	        }

	        return subscriptions;
	    }

	@Override
	public int getSubscriptionCount(long mem_num) {
		return subscriptionMapper.getSubscriptionCount(mem_num);
	}
}
