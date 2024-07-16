package kr.spring.cs.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.cs.vo.InquiryVO;

@Mapper
public interface CSMapper {
	//문의 작성
	public void insertInquiry(InquiryVO inquiryVO);
	//문의 상세
	public InquiryVO selectInquiryDetail(long inquiry_num);
	//문의 목록
	public List<InquiryVO> selectInquiryList(Map<String, Object> map);
	//문의 개수
	public int selectInquiryListCount(Map<String, Object> map);
	//회원별 문의 목록
	@Select("SELECT * FROM inquiry WHERE mem_num=#{mem_num}")
	public List<InquiryVO> selectInquiryListByMemNum(long mem_num);
	//문의 수정
	public void updateInquiry(InquiryVO inquiryVO);
	//문의 삭제
	public void deleteInquiry(long inquiry_num);
}
