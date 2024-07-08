package kr.spring.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import kr.spring.member.vo.KakaoInfo;

public interface MemberOAuthService {
	public String getKakaoAccessToken(String code) throws JsonMappingException, JsonProcessingException;
	public KakaoInfo getKakaoInfo(String accessToken) throws JsonMappingException, JsonProcessingException;
	public void kakaoDisconnect(String accessToken) throws JsonMappingException, JsonProcessingException;
}
