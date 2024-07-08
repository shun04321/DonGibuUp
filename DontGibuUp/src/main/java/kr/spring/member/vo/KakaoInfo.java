package kr.spring.member.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class KakaoInfo {
	private Long id;
	private String email;

	public KakaoInfo(Long id, String email) {
		this.id = id;
		this.email = email;
	}
}
