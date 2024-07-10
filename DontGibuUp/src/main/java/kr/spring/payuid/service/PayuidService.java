package kr.spring.payuid.service;

import kr.spring.payuid.vo.PayuidVO;
public interface PayuidService {
	
	public void registerPayUId(PayuidVO payuidVO);
	public void deletePayUId (long mem_num);
	public String getPayUId(long mem_num);
}
