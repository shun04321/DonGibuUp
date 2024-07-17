package kr.spring.cs.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.cs.vo.InquiryVO;

@Mapper
public interface CSMapper {
	//문의 작성
	public void insertInquiry(InquiryVO inquiryVO);
	//문의 상세
	@Select("SELECT * FROM inquiry WHERE inquiry_num=#{inquiry_num}")
	public InquiryVO selectInquiryDetail(long inquiry_num);
	//문의 목록(관리자)
	public List<InquiryVO> selectInquiryList(Map<String, Object> map);
	//문의 개수(관리자)
	@Select("SELECT COUNT(*) FROM inquiry")
	public int selectInquiryListCount();
	//회원별 문의 목록
	@Select("SELECT * FROM inquiry WHERE mem_num=#{mem_num}")
	public List<InquiryVO> selectInquiryListByMemNum(long mem_num);
	//문의 수정
	public void updateInquiry(InquiryVO inquiryVO);
	//문의 삭제
	@Delete("DELETE FROM inquiry WHERE inquiry_num=#{inquiry_num}")
	public void deleteInquiry(long inquiry_num);
	
	
}
