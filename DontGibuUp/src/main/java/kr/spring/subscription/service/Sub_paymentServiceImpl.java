package kr.spring.subscription.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.subscription.dao.Sub_paymentMapper;
import kr.spring.subscription.vo.Sub_paymentVO;

@Service
@Transactional
public class Sub_paymentServiceImpl implements Sub_paymentService{
	@Autowired
	Sub_paymentMapper Sub_paymentMapper;
	
	@Override
	public void insertSub_payment(Sub_paymentVO subpaymentVO) {
		Sub_paymentMapper.insertSub_payment(subpaymentVO);
	}

	@Override
	public long getSub_payment_num() {
		return Sub_paymentMapper.getSub_payment_num();
	}

}
