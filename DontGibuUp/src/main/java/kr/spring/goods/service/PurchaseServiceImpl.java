package kr.spring.goods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.spring.goods.dao.PurchaseMapper;
import kr.spring.goods.vo.PurchaseVO;
import kr.spring.goods.vo.RefundVO;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Override
    public void insertPurchase(PurchaseVO purchaseVO) {
        purchaseMapper.insertPurchase(purchaseVO);
    }
    @Override
    public void processRefund(RefundVO refundVO){
        purchaseMapper.insertRefund(refundVO);
    }
    @Override
    public List<PurchaseVO> getPurchaseListByMember(long memNum) {
        return purchaseMapper.getPurchaseListByMember(memNum);
    }
    @Override
    public void updateRefundStatus(String impUid, int status) {
        purchaseMapper.updateRefundStatus(impUid, status);
    }
    @Override
    public List<PurchaseVO> getAllPurchases() {
        return purchaseMapper.getAllPurchases();
    }

    @Override
    public void updateDeliveryStatus(int purchaseNum, String deliveryStatus) {
        purchaseMapper.updateDeliveryStatus(purchaseNum, deliveryStatus);
    }
}