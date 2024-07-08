package kr.spring.subscription.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.subscription.dao.SubscriptionMapper;

@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {
	@Autowired
	SubscriptionMapper SubscriptionMapper;

	
}
