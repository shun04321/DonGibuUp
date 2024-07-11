package kr.spring.payuid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.payuid.dao.PayuidMapper;
import kr.spring.payuid.vo.PayuidVO;

@Service
@Transactional
public class PayuidServiceImpl implements PayuidService{
	
	@Autowired
	PayuidMapper payuidMapper;
	
	@Override
	public void registerPayUId(PayuidVO payuidVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePayUId(long mem_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPayUId(long mem_num) {
		return payuidMapper.getPayUId(mem_num);
	}


}
