package kr.spring.payuid.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.spring.category.vo.DonationCategoryVO;
import kr.spring.payuid.vo.PayUIdVO;
public interface PayuidService {
	public void registerPayUId(PayUIdVO payuidVO);
	public void deletePayUId (long mem_num);
	public String getPayUId(long mem_num);
}
