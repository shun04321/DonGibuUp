package kr.spring.payuid.service;

import java.util.List;

import kr.spring.payuid.vo.PayuidVO;
public interface PayuidService {
	
	public void registerPayUId(PayuidVO payuidVO);
	public void deletePayUId (long mem_num);
	public List<PayuidVO> getPayUId(long mem_num);
	public int getCountPayuidByMethod(PayuidVO payuidVO);
}
