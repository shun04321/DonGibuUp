package kr.spring.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.member.vo.MemberVO;
import kr.spring.point.vo.PointVO;

@Mapper
public interface MemberMapper {
	//회원관리
	//회원 시퀀스 생성
	@Select("SELECT member_seq.nextval FROM DUAL")
	public long selectMemNum();
	
	//회원가입
	public void insertMember(MemberVO memberVO);
	public void insertMemberDetail(MemberVO memberVO);

	//회원 이메일로 회원정보 가져오기
	@Select("SELECT * FROM member WHERE mem_email=#{mem_eamil}")
	public MemberVO selectMemberByEmail(String mem_email);
	
	//추천코드로 회원정보 가져오기
	@Select("SELECT mem_num FROM member_detail WHERE mem_rcode=#{friend_rcode}")
	public long selectMemNumByRCode(String friend_rcode);
	
	//회원 포인트 업데이트
	@Update("UPDATE member_detail SET mem_point=mem_point+#{point_amount} WHERE mem_num=#{mem_num}")
	public void updateMemPoint(PointVO pointVO);
	
	//중복 추천인 코드 체크
	@Select("SELECT COUNT(*) FROM member_detail WHERE mem_rcode=#{rcode}")
	public int checkRCodeExists(String rcode);
}
