package kr.spring.goods.service;

import java.util.List;

import kr.spring.goods.vo.PurchaseVO;
import kr.spring.goods.vo.RefundVO;

public interface PurchaseService {
    void insertPurchase(PurchaseVO purchaseVO);

    void processRefund(RefundVO refundVO);
    List<PurchaseVO> getPurchaseListByMember(long memNum);
    
    List<PurchaseVO> getAllPurchases();
    void updateDeliveryStatus(int purchaseNum, String deliveryStatus);
    void updateRefundStatus(String impUid, int status);
    
}