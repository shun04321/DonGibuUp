package kr.spring.dbox.service;

import kr.spring.dbox.vo.DboxBudgetVO;
import kr.spring.dbox.vo.DboxVO;

public interface DboxService {
	//Dbox 입력
	public Long insertDbox(DboxVO dbox);
}