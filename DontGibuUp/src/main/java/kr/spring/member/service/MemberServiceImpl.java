package kr.spring.member.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.member.dao.MemberMapper;
import kr.spring.member.vo.MemberVO;
import kr.spring.point.dao.PointMapper;
import kr.spring.point.service.PointService;
import kr.spring.point.vo.PointVO;
import kr.spring.util.RCodeGenerator;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	PointMapper pointMapper;
	@Autowired
	PointService pointService;

	//회원가입
	@Override
	public void insertMember(MemberVO memberVO, PointVO pointVO) {
		//mem_num 지정
		long mem_num = memberMapper.selectMemNum();
		memberVO.setMem_num(mem_num);
		pointVO.setMem_num(mem_num);
		
		//member 추가
		memberMapper.insertMember(memberVO);
		//member_detail 추가
		memberMapper.insertMemberDetail(memberVO);
		//포인트 적립
		pointMapper.insertPointLog(pointVO);
		//추천인 이벤트 참여시
		//memberMapper.updateMemPoint(pointVO2);
		
	}

	//이메일로 회원 찾기(기존회원 체크)
	@Override
	public MemberVO selectMemberByEmail(String mem_email) {
		return memberMapper.selectMemberByEmail(mem_email);
	}
	
	//추천인코드 만들기
	@Override
    public String generateUniqueRCode() {
        String rcode;
        boolean isUnique;
        do {
            rcode = RCodeGenerator.generateRCode();
            isUnique = checkIfRCodeIsUnique(rcode);
        } while (!isUnique);
        return rcode;
    }
    
	//추천인코드 중복확인
	@Override
	public boolean checkIfRCodeIsUnique(String rcode) {
		//mapper에서 중복 체크
		if (memberMapper.checkRCodeExists(rcode) == 0) {
			//중복아님 = unique
			return true;
		} else {
			//중복 = not unique
			return false;
		}
	}
	
	
}
