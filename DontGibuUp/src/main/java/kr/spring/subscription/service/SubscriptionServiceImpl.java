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
	public void endSubscription(long sub_num) {
		SubscriptionMapper.endSubscription(sub_num);
		
	}

	@Override
	public long getSubscription(long sub_num) {
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

	
}
