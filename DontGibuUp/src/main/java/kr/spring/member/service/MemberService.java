package kr.spring.member.service;

import kr.spring.member.vo.MemberVO;
import kr.spring.point.vo.PointVO;
import kr.spring.util.RCodeGenerator;

public interface MemberService {
	public void insertMember(MemberVO memberVO, PointVO pointVO);
	public MemberVO selectMemberByEmail(String mem_email);
	
	//추천인코드 만들기
    public String generateUniqueRCode();
	//추천인코드 중복확인
	public boolean checkIfRCodeIsUnique(String rcode);
}
