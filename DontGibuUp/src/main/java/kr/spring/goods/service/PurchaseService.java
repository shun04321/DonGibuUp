package kr.spring.goods.service;

import java.util.List;

import kr.spring.cart.vo.CartVO;
import kr.spring.goods.vo.PurchaseVO;
import kr.spring.goods.vo.RefundVO;

public interface PurchaseService {
	void insertPurchase(PurchaseVO purchaseVO);
    List<CartVO> getPurchaseItems(long purchase_num);
    void processRefund(RefundVO refundVO);
    List<PurchaseVO> getPurchaseListByMember(long memNum);
    List<PurchaseVO> getAllPurchases();
    void updateDeliveryStatus(int purchaseNum, String deliveryStatus);
    void updateRefundStatus(String impUid, int status);
    void insertPurchaseWithCartItems(PurchaseVO purchaseVO);
    void insertPurchaseItems(CartVO cartVO);
    Long getSeq();
    void updateDeliveryStatusByImpUid(String impUid, String deliveryStatus);
}