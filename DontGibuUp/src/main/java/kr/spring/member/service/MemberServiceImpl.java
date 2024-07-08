package kr.spring.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.member.dao.MemberMapper;
import kr.spring.member.vo.MemberVO;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberMapper memberMapper;

	@Override
	public void insertMember(MemberVO memberVO) {
		memberMapper.insertMember(memberVO);
	}

	@Override
	public MemberVO selectMemberByEmail(String mem_email) {
		return memberMapper.selectMemberByEmail(mem_email);
	}
	
	
}
