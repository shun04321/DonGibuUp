package kr.spring.goods.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.goods.vo.PurchaseVO;
@Mapper
public interface PurchaseMapper {
    void insertPurchase(PurchaseVO purchaseVO);
}