package kr.spring.payuid.service;

import java.util.List;

import kr.spring.payuid.vo.PayuidVO;
public interface PayuidService {
	
	public void registerPayUId(PayuidVO payuidVO);
	public void deletePayuid (String pay_uid);
	public List<PayuidVO> getPayUId(long mem_num);
	public PayuidVO getPayuidByMethod(PayuidVO payuidVO);
	public PayuidVO getPayuidVOByPayuid(String pay_uid);
}
