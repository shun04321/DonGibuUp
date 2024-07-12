package kr.spring.payuid.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.payuid.vo.PayuidVO;

@Mapper
public interface PayuidMapper {
	// 결제 카드 등록 (빌링키 발급)
	public void registerPayUId(PayuidVO payuidVO);
	// 결제 카드 삭제 (빌링키 삭제, 수정불가능 재발급 필요)
	@Delete("DELETE FROM pay_uid WHERE mem_num=#{mem_num}")
	public void deletePayUId (long mem_num);
	//정기 결제를 위한 빌링키 가져오기
	@Select("SELECT pay_uid FROM pay_uid WHERE mem_num=#{mem_num}")
	public String getPayUId(long mem_num);
}
