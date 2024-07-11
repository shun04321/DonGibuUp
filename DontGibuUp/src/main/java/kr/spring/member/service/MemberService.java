package kr.spring.member.service;

import kr.spring.member.vo.MemberVO;

public interface MemberService {
	//회원가입
	public void insertMember(MemberVO memberVO);
	
	//비밀번호 비교하기
	public boolean isCheckedPassword(MemberVO member, String rawPassword);
	
	//회원정보 가져오기
	public MemberVO selectMember(Long mem_num);
	//회원정보+디테일 가져오기
	public MemberVO selectMemberDetail(Long mem_num);
	
	//이메일로 회원정보 가져오기
	public MemberVO selectMemberByEmail(String mem_email);
	
	//회원 닉네임으로 회원정보 가져오기(중복 닉네임 체크)
	public MemberVO selectMemberByNick(String mem_nick);
	
	//추천인코드 만들기
    public String generateUniqueRCode();
	//추천인코드 중복확인
	public boolean checkIfRCodeIsUnique(String rcode);
	//추천인 코드 대조
	public Long selectMemNumByRCode(String friend_rcode);
	
	//프로필사진 업데이트
	public void updateMemPhoto(MemberVO memberVO);
	
	//회원정보 수정
	public void updateMember(MemberVO memberVO);
}
