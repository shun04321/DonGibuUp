package kr.spring.category.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.category.vo.DonationCategoryVO;

@Mapper
public interface CategoryMapper {
	// 기부 카테고리 등록
	public void insertDonationCategory(DonationCategoryVO donationCategoryVO);
	// 기부 카테고리 개수
	@Select("SELECT COUNT(*) FROM dona_category")
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
