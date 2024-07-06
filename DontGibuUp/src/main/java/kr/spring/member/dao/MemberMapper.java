package kr.spring.member.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.member.vo.MemberVO;

@Mapper
public interface MemberMapper {
	public void insertMember(MemberVO memberVO);
}
