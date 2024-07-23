package kr.spring.subscription.service;

import java.util.List;

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

	@Override
	public Sub_paymentVO getSub_paymentByDate(long mem_num) {
		return Sub_paymentMapper.getSub_paymentByDate(mem_num);
	}

	@Override
	public List<Sub_paymentVO> getSub_payment() {
		return Sub_paymentMapper.getSub_payment();
	}

	@Override
	public List<Sub_paymentVO> getSub_paymentByMem_num(long mem_num) {
		return Sub_paymentMapper.getSub_paymentByMem_num(mem_num);
	}

	@Override
	public int getSub_paymentCountByMem_num(long mem_num) {
		return Sub_paymentMapper.getSub_paymentCountByMem_num(mem_num);
	}

}
