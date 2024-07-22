package kr.spring.member.service;


import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.member.dao.MemberMapper;
import kr.spring.member.vo.MemberVO;
import kr.spring.point.service.PointService;
import kr.spring.point.vo.PointVO;
import kr.spring.util.RCodeGenerator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	MemberService memberService;
	@Autowired
	PointService pointService;
	@Autowired
	PasswordEncoder pwEncoder;

	//회원가입
	@Override
	public void insertMember(MemberVO memberVO) {
		//mem_num 지정
		long mem_num = memberMapper.selectMemNum();
		memberVO.setMem_num(mem_num);
		//추천인 코드지정
		memberVO.setMem_rcode(generateUniqueRCode());
		
		//일반 회원가입 비밀번호 해싱
		if (memberVO.getMem_reg_type() == 1) {
			String encpassword = pwEncoder.encode(memberVO.getMem_pw());
			memberVO.setMem_pw(encpassword);
		}
		
		//member 추가
		memberMapper.insertMember(memberVO);
		//member_detail 추가(default point = 1000)
		memberMapper.insertMemberDetail(memberVO);
		
		//회원가입 포인트 로그
		PointVO point_signup = new PointVO(12, 1000); //포인트타입 12:회원가입
		log.debug("<<회원가입 - 포인트>> : " + point_signup);
		point_signup.setMem_num(mem_num);
		
		//회원가입 포인트 로그 추가
		pointService.insertPointLog(point_signup);
		
		//추천인 이벤트 참여
		if (memberVO.getRecommend_status() == 1) {
			long recipientMemNum = memberMapper.selectMemNumByRCode(memberVO.getFriend_rcode());

			
			PointVO point_revent1 = new PointVO(10, 3000, mem_num);
			PointVO point_revent2 = new PointVO(10, 3000, recipientMemNum);
			
			//포인트 로그 추가
			pointService.insertPointLog(point_revent1);
			pointService.insertPointLog(point_revent2);
			
			//member_detail 업데이트
			memberService.updateMemPoint(point_revent1);
			memberService.updateMemPoint(point_revent2);
		}
		
	}
	
	//비밀번호 비교하기
	@Override
	public boolean isCheckedPassword(MemberVO member, String rawPassword) {
		return pwEncoder.matches(rawPassword, member.getMem_pw());
	}
	
	@Override
	public MemberVO selectMember(Long mem_num) {
		return memberMapper.selectMember(mem_num);
	}
	@Override
	public MemberVO selectMemberDetail(Long mem_num) {
		return memberMapper.selectMemberDetail(mem_num);
	}

	//이메일로 회원 찾기(기존회원 체크)
	@Override
	public MemberVO selectMemberByEmail(String mem_email) {
		return memberMapper.selectMemberByEmail(mem_email);
	}
	
	//닉네임으로 회원 찾기(기존회원 체크)
	@Override
	public MemberVO selectMemberByNick(String mem_nick) {
		return memberMapper.selectMemberByNick(mem_nick);
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
	
	//추천인 코드 대조
	@Override
	public Long selectMemNumByRCode(String friend_rcode) {
		return memberMapper.selectMemNumByRCode(friend_rcode);
	}

	@Override
	public void updateMemPhoto(MemberVO memberVO) {
		memberMapper.updateMemPhoto(memberVO);
	}

	@Override
	public void updateMember(MemberVO memberVO) {
		memberMapper.updateMember(memberVO);
		memberMapper.updateMemberDetail(memberVO);
	}
	

	@Override
	public void updateMemPoint(PointVO pointVO) {
		memberMapper.updateMemPoint(pointVO);
	}


	//비밀번호 수정
	@Override
	public void updatePassword(MemberVO memberVO) {
		//비밀번호 해싱
		String encpassword = pwEncoder.encode(memberVO.getMem_pw());
		memberVO.setMem_pw(encpassword);
		
		memberMapper.updatePassword(memberVO);
	}

	//임시 비밀번호 설정
	@Override
	public String SetTempPassword(MemberVO memberVO) {
		//임시 비밀번호 생성
		String tempPassword = createCode();
		memberVO.setMem_pw(tempPassword);
		
		//비밀번호 해싱
		String encpassword = pwEncoder.encode(memberVO.getMem_pw());
		memberVO.setMem_pw(encpassword);
		
		memberMapper.updatePassword(memberVO);
		
		return tempPassword;
	}
	

    //임시 비밀번호 생성 메서드
    private String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0: key.append((char) ((int) random.nextInt(26) + 97)); break;
                case 1: key.append((char) ((int) random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }

    
    //관리자
	@Override
	public List<MemberVO> selectMemberList(Map<String, Object> map) {
		return memberMapper.selectMemberList(map);
	}

	@Override
	public int selectMemberCount(Map<String, Object> map) {
		return memberMapper.selectMemberCount(map);
	}

	@Override
	public void updateMemStatus(MemberVO memberVO) {
		memberMapper.updateMemStatus(memberVO);
	}

	
}
