package kr.spring.category.service;

import java.util.List;
import java.util.Map;

import kr.spring.category.vo.DonationCategoryVO;

public interface CategoryService {
	// 기부 카테고리 등록
		public void insertDonationCategory(DonationCategoryVO donationCategoryVO);
		// 기부 카테고리 수
		public int getListCount(Map<String,Object> map);
		// 기부 카테고리 목록
		public List<DonationCategoryVO> selectList(Map<String,Object> map);
		// 기부 카테고리 상세
		public DonationCategoryVO selectDonationCategory(Long dcate_num);
		// 기부 카테고리 수정 
		public void updateDonationCategory(DonationCategoryVO donationCategoryVO);
		// 기부 카테고리 삭제
		public void deleteDonationCategory(Long dcate_num);
}
