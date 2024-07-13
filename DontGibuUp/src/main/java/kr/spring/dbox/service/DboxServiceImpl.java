package kr.spring.dbox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.dbox.dao.DboxMapper;
import kr.spring.dbox.vo.DboxVO;

@Service
@Transactional
public class DboxServiceImpl implements DboxService {
	@Autowired
	DboxMapper dboxMapper;

	@Override
	public void insertDbox(DboxVO dbox) {
		dboxMapper.insertDbox(dbox);
	}
}