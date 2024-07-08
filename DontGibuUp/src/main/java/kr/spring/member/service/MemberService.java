package kr.spring.member.service;

import kr.spring.member.vo.MemberVO;

public interface MemberService {
	public void insertMember(MemberVO memberVO);
	public MemberVO selectMemberByEmail(String mem_email);
}
