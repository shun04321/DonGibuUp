package kr.spring.cs.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.cs.dao.CSMapper;
import kr.spring.cs.vo.FaqVO;
import kr.spring.cs.vo.InquiryVO;
import kr.spring.cs.vo.ReportVO;

@Service
@Transactional
public class CSServiceImpl implements CSService {
	@Autowired
	CSMapper csMapper;

	@Override
	public void insertInquiry(InquiryVO inquiryVO) {
		csMapper.insertInquiry(inquiryVO);
	}

	@Override
	public InquiryVO selectInquiryDetail(long inquiry_num) {
		return csMapper.selectInquiryDetail(inquiry_num);
	}

	@Override
	public List<InquiryVO> selectInquiryList(Map<String, Object> map) {
		return csMapper.selectInquiryList(map);
	}
	
	@Override
	public int selectInquiryListCount(Map<String, Object> map) {
		return csMapper.selectInquiryListCount(map);
	}

	@Override
	public List<InquiryVO> selectInquiryListByMemNum(long mem_num) {
		return csMapper.selectInquiryListByMemNum(mem_num);
	}

	@Override
	public void updateInquiry(InquiryVO inquiryVO) {
		csMapper.updateInquiry(inquiryVO);
	}

	@Override
	public void deleteInquiry(long inquiry_num) {
		csMapper.deleteInquiry(inquiry_num);
	}

	@Override
	public void replyInquiry(InquiryVO inquiryVO) {
		csMapper.replyInquiry(inquiryVO);
	}

	@Override
	public List<FaqVO> selectFaqList(Map<String, Object> map) {
		return csMapper.selectFaqList(map);
	}

	@Override
	public long insertFaq(FaqVO faqVO) {
		long faq_num = csMapper.selectFaqNum();
		
		faqVO.setFaq_num(faq_num);
		csMapper.insertFaq(faqVO);
		
		return faq_num;
	}

	@Override
	public void updateFaq(FaqVO faqVO) {
		csMapper.updateFaq(faqVO);
	}

	@Override
	public void deleteFaq(long faq_num) {
		csMapper.deleteFaq(faq_num);
	}

	@Override
	public int selectReportListCount(Map<String, Object> map) {
		return csMapper.selectReportListCount(map);
	}

	@Override
	public List<ReportVO> selectReportList(Map<String, Object> map) {
		return csMapper.selectReportList(map);
	}

	@Override
	public ReportVO selectReport(long report_num) {
		return csMapper.selectReport(report_num);
	}

	@Override
	public void replyReport(ReportVO reportVO) {
		csMapper.replyReport(reportVO);
	}

	@Override
	public void deleteReport(long report_num) {
		csMapper.deleteReport(report_num);
	}

	@Override
	public List<ReportVO> selectReportListByMemNum(Map<String, Object> map) {
		return csMapper.selectReportListByMemNum(map);
	}

}
