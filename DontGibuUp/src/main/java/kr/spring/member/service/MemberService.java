package kr.spring.member.service;

import kr.spring.member.vo.MemberVO;

public interface MemberService {
	public void insertMember(MemberVO memberVO);
	//이메일로 회원정보 가져오기
	public MemberVO selectMemberByEmail(String mem_email);
	
	//추천인코드 만들기
    public String generateUniqueRCode();
	//추천인코드 중복확인
	public boolean checkIfRCodeIsUnique(String rcode);
	
	//프로필사진 업데이트
	public void updateMemPhoto(MemberVO memberVO);
	
	//회원정보 수정
	public void updateMember(MemberVO memberVO);
}
