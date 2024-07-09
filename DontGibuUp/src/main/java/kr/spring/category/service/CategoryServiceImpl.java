package kr.spring.category.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.category.dao.CategoryMapper;
import kr.spring.category.vo.DonationCategoryVO;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryMapper categoryMapper;

	@Override
	public void insertDonationCategory(DonationCategoryVO donationCategoryVO) {
		categoryMapper.insertDonationCategory(donationCategoryVO);
	}

	@Override
	public List<DonationCategoryVO> selectList(Map<String, Object> map) {
		return categoryMapper.selectList(map);
	}

	@Override
	public DonationCategoryVO selectDonationCategory(Long dcate_num) {
		return categoryMapper.selectDonationCategory(dcate_num);
	}

	@Override
	public void updateDonationCategory(DonationCategoryVO donationCategoryVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDonationCategory(Long dcate_num) {
		categoryMapper.deleteDonationCategory(dcate_num);
	}

	@Override
	public int getListCount(Map<String, Object> map) {
		return categoryMapper.getListCount(map);
	}

}
