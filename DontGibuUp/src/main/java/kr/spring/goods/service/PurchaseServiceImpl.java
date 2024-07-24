package kr.spring.goods.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.cart.vo.CartVO;
import kr.spring.goods.dao.PurchaseMapper;
import kr.spring.goods.vo.PurchaseVO;
import kr.spring.goods.vo.RefundVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;
    
    @Override
    public void insertPurchase(PurchaseVO purchaseVO) {
        purchaseMapper.insertPurchase(purchaseVO);
    }

    @Override
    @Transactional
    public void insertPurchaseWithCartItems(PurchaseVO purchaseVO) {
        // 1. purchase 테이블에 데이터 삽입
        purchaseMapper.insertPurchaseWithCartItems(purchaseVO);
     // 2. 각 CartVO에 purchaseNum 설정 및 purchase_item 테이블에 삽입
        for (CartVO cartItem : purchaseVO.getCart_items()) {
            cartItem.setPurchase_num(purchaseVO.getPurchaseNum());
            purchaseMapper.insertPurchaseItems(cartItem);
        }
   
    }
    @Override
    public List<CartVO> getPurchaseItems(long purchase_num) {
        return purchaseMapper.getPurchaseItems(purchase_num);
    }

    @Override
    public void processRefund(RefundVO refundVO) {
        purchaseMapper.insertRefund(refundVO);
    }

    public List<PurchaseVO> getPurchaseListByMember(long memNum) {
        List<PurchaseVO> purchaseList = purchaseMapper.getPurchaseListByMember(memNum);
        for (PurchaseVO purchase : purchaseList) {
            if (purchase.getPurchaseNum() != null) {
                List<CartVO> cartItems = purchaseMapper.getPurchaseItems(purchase.getPurchaseNum());
                purchase.setCart_items(cartItems);
            } else {
                log.error("purchaseNum is null for purchase: " + purchase);
            }
        }
        return purchaseList;
    }

    @Override
    public List<PurchaseVO> getAllPurchases() {
        return purchaseMapper.getAllPurchases();
    }

    @Override
    public void updateDeliveryStatus(int purchaseNum, String deliveryStatus) {
        purchaseMapper.updateDeliveryStatus(purchaseNum, deliveryStatus);
    }

    @Override
    public void updateRefundStatus(String impUid, int status) {
        purchaseMapper.updateRefundStatus(impUid, status);
    }


	@Override
	public long getSeq() {
		return purchaseMapper.getSeq();
	}

	@Override
	public void insertPurchaseItems(CartVO cartItem) {
		purchaseMapper.insertPurchaseItems(cartItem);
		
	}

	
}