package kr.spring.refund.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.payuid.vo.PayuidVO;
import kr.spring.refund.vo.RefundVO;

@Mapper
public interface RefundMapper {
	//환불 신청
	public void insertRefund(RefundVO refundVO);
	
	//관리자 환불신청 목록 조회
	public List<RefundVO> getRefundList();
	
}
