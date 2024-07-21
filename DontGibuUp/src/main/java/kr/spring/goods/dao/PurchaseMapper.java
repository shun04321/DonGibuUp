package kr.spring.goods.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.goods.vo.PurchaseVO;
import kr.spring.goods.vo.RefundVO;
@Mapper
public interface PurchaseMapper {
    void insertPurchase(PurchaseVO purchaseVO);
    void insertRefund(RefundVO refundVO);
    List<PurchaseVO> getPurchaseListByMember(long memNum);
}