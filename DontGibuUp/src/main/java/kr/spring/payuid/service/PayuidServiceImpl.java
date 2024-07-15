package kr.spring.payuid.service;

import java.util.List;

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
		payuidMapper.registerPayUId(payuidVO);
	}

	@Override
	public void deletePayUId(long mem_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PayuidVO> getPayUId(long mem_num) {
		return payuidMapper.getPayUId(mem_num);
	}

	@Override
	public int getCountPayuidByMethod(PayuidVO payuidVO) {
		return payuidMapper.getCountPayuidByMethod(payuidVO);
	}


}
