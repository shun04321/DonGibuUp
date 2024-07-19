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

import kr.spring.subscription.dao.SubscriptionMapper;
import kr.spring.subscription.vo.SubscriptionVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionMapper subscriptionMapper;

    private static final String IMP_KEY = "1607808574122853";
    private static final String IMP_SECRET = "pRNB4gue3y2Y7MvkiLRCrqECvVseUrmFKo1XtMokvRAj5LCOZ9zFLFIOAcYTZQbApYFMPRMoNfo8R5NE";

    @Override
    public String schedulePay(String customerUid, int price, String merchant_uid) {
        String token = getToken();
        Integer timestamp = 0;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.KOREA);
		cal.add(Calendar.DATE, +5);
		String date = sdf.format(cal.getTime());
		try {
			Date stp = sdf.parse(date);
			timestamp = (int) (stp.getTime()/1000);
			System.out.println(timestamp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 현재 Unix Timestamp를 초 단위로 생성
        String accessToken = extractAccessToken(token);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("merchant_uid", merchant_uid);
        jsonObject.addProperty("schedule_at", timestamp); // 현재 Unix Timestamp 사용
        jsonObject.addProperty("amount", price);

        JsonObject reqJson = new JsonObject();
        reqJson.addProperty("customer_uid", customerUid);
        reqJson.add("schedules", jsonObject);

        String json = new Gson().toJson(reqJson);

        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        String response =  restTemplate.postForObject("https://api.iamport.kr/subscribe/payments/schedule", entity, String.class);

        // 응답 결과를 콘솔에 출력
        System.out.println("스케줄페이 응답: " + response);

        return response;

    }


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



    private String extractAccessToken(String token) {
        try {
            JsonObject jsonObject = new Gson().fromJson(token, JsonObject.class);
            return jsonObject.getAsJsonObject("response").get("access_token").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
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
        return subscriptionMapper.getSubscription(sub_num);
    }

    @Override
    public void deleteSubscription(long sub_num) {
        subscriptionMapper.deleteSubscription(sub_num);
    }

    @Override
    public String getTodayDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        return sdf.format(new Date());
    }

    private ThreadPoolTaskScheduler scheduler;

    public void stopScheduler() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    public void startScheduler(String customerUid, int price, String merchant_uid) {
    	System.out.println("스케줄러 스타터 정상 작동, 데이터" + customerUid + price + merchant_uid);
        if (scheduler == null) {
            scheduler = new ThreadPoolTaskScheduler();
            scheduler.initialize();
        }
        scheduler.schedule(getRunnable(customerUid, price, merchant_uid), getTrigger());
    }

    private Runnable getRunnable(String customerUid, int price, String merchant_uid) {
        return () -> {
            try {
            	System.out.println("getRunnable 정상 작동, 데이터" +","+customerUid +","+ price +","+ merchant_uid);
                schedulePay(customerUid, price, merchant_uid);
            } catch (Exception e) {
                e.printStackTrace(); // 로그 기록
            }
        };
    }

    private Trigger getTrigger() {
        return new PeriodicTrigger(1, TimeUnit.MINUTES);
    }
}
