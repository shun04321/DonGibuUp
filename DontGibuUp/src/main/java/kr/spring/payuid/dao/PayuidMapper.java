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
	// 결제 수단 등록 (빌링키 발급)
	public void registerPayUId(PayuidVO payuidVO);
	
	// 결제 카드 삭제 (빌링키 삭제, 수정불가능 재발급 필요)
	@Delete("DELETE FROM pay_uid WHERE mem_num=#{mem_num}")
	public void deletePayUId (long mem_num,);
	
	//등록된 카드 가져오기
	@Select("SELECT * FROM pay_uid WHERE mem_num=#{mem_num}")
	public List<PayuidVO> getPayUId(long mem_num);
	
	//선택한 결제수단과 mem_num으로 payuid 유무 확인
	public int getCountPayuidByMethod(PayuidVO payuidVO);
	// 없으면 해당하는 플랫폼의 payuid 발급 페이지, 
	// 새카드 등록 눌렀을때도 토스의 카드 payuid 발급 페이지,   

	//--> payuid 발급 페이지는 무조건 getPayuid.jsp 하지만 subscriptionVO로 받아온
	// sub_method와 easypay_method에 따라 pg를 다르게 전달함.
	
	
	// 선행조건 1. -> uuid 생성 메소드를 컨트롤러에 만들고 easypay_method의 payuid 유무, 
	// 새 카드 등록에 따라 사용. 만약 response가 실패로 돌아오면 삭제.
	
	// 선행조건 2. -> 오늘 날짜 구하기 메소드를 컨트롤러에 만들고, sub_ndate는 오늘 날짜로 선택,
	// 즉 오늘 날짜가 정기기부 날짜임을 폼에서 표시, 추후 mypage 정기기부 목록에서 수정할 수 있게 변경
	
	// 정기기부시 결제수단에 계좌이체, 핸드폰도 가능한 것 같으니 확인.
}
