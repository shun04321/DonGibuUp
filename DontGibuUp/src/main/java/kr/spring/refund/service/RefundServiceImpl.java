package kr.spring.refund.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.refund.dao.RefundMapper;

@Service
@Transactional
public class RefundServiceImpl implements RefundService{
	@Autowired
	RefundMapper refundMapper;
}
