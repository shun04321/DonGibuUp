package kr.spring.subscription.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.subscription.dao.SubscriptionMapper;
import kr.spring.subscription.vo.SubscriptionVO;

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
	public void endSubscription(long mem_num, long sub_num) {
		SubscriptionMapper.endSubscription(mem_num, sub_num);
		
	}

	@Override
	public long getSubscriptionNum(long mem_num, long dcate_num) {
		return SubscriptionMapper.getSubscriptionNum(mem_num, dcate_num);
	}

	
}
