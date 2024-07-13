package kr.spring.dbox.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.dbox.vo.DboxVO;

@Mapper
public interface DboxMapper {
	public void insertDbox(DboxVO dbox);
}