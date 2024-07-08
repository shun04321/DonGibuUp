package kr.spring.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.member.vo.MemberVO;

@Mapper
public interface MemberMapper {
	public void insertMember(MemberVO memberVO);
	@Select("SELECT * FROM member WHERE mem_email=#{mem_eamil}")
	public MemberVO selectMemberByEmail(String mem_email);
}
