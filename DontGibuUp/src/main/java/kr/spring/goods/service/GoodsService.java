package kr.spring.goods.service;

import java.util.List;
import java.util.Map;

import kr.spring.goods.vo.GoodsVO;
import kr.spring.goods.vo.PaymentVO;

public interface GoodsService {
    public List<GoodsVO> selectList(Map<String, Object> map, Integer mem_status);
    public void insertGoods(GoodsVO goodsVO);
    public Integer selectRowCount(Map<String, Object> map);
    public void updateGoods(GoodsVO goodsVO);
    public GoodsVO detailGoods(long item_num);
    public void deleteGoods(long item_num);

    // 결제 관련 메서드
    public void insertPayment(PaymentVO paymentVO);
    public void processPayment(PaymentVO paymentVO) throws Exception;
    public void processRefund(String impUid, int amount, String reason) throws Exception;
}