package kr.spring.member.service;

import kr.spring.member.vo.MemberVO;
import kr.spring.point.vo.PointVO;

public interface MemberService {
	public void insertMember(MemberVO memberVO, PointVO pointVO);
	public MemberVO selectMemberByEmail(String mem_email);
}
