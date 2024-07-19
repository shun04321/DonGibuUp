package kr.spring.goods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import kr.spring.goods.service.PurchaseService;
import kr.spring.goods.vo.PurchaseVO;
import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/goods")
@Slf4j
public class PurchaseController {

    private IamportClient impClient;

    @Value("${iamport.apiKey}")
    private String apiKey;

    @Value("${iamport.secretKey}")
    private String secretKey;

    @Autowired
    private PurchaseService purchaseService;

    @PostConstruct
    public void initImp() {
        this.impClient = new IamportClient(apiKey, secretKey);
    }

    @PostMapping("/purchase")
    public String purchasePage(@RequestParam("imp_uid") String impUid, Model model) {
        model.addAttribute("imp_uid", impUid);
        return "goods/purchase";
    }

    @GetMapping("/purchase")
    public String getPurchasePage() {
        return "goods/purchase";
    }

    // 결제 정보 검증
    @PostMapping("/paymentVerify/{imp_uid}")
    @ResponseBody
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid, HttpSession session, HttpServletRequest request)
            throws IamportResponseException, IOException {
        IamportResponse<Payment> payment = impClient.paymentByImpUid(imp_uid);

        // 로그인 여부 확인하기
        MemberVO member = (MemberVO) session.getAttribute("user");

        // 실 결제 금액 가져오기
        long paidAmount = payment.getResponse().getAmount().longValue();

        log.debug("payment: " + payment);

        return payment;
    }

    // 결제 정보 저장
    @PostMapping("/purchaseComplete")
    @ResponseBody
    public Map<String, String> savePurchaseInfo(@RequestBody Map<String, Object> data, HttpSession session, HttpServletRequest request)
            throws IllegalStateException, IOException {
        String impUid = (String) data.get("imp_uid");
        String merchantUid = (String) data.get("merchant_uid");
        int amount = (Integer) data.get("amount");
        String status = (String) data.get("status");
        int itemNum = (Integer) data.get("item_num");
        String itemName = (String) data.get("item_name");
        String buyerName = (String) data.get("buyer_name");
        String buyerEmail = (String) data.get("buyer_email");

        log.debug("impUid : " + impUid);
        log.debug("merchantUid : " + merchantUid);
        log.debug("amount : " + amount);
        log.debug("status : " + status);
        log.debug("itemNum : " + itemNum);
        log.debug("itemName : " + itemName);
        log.debug("buyerName : " + buyerName);
        log.debug("buyerEmail : " + buyerEmail);

        Map<String, String> mapJson = new HashMap<>();

        // 세션 데이터 가져오기
        MemberVO member = (MemberVO) session.getAttribute("user");

        if (member == null) {
            mapJson.put("result", "logout");
        } else {
            // 결제 정보 저장
            PurchaseVO purchaseVO = new PurchaseVO();
            purchaseVO.setImp_uid(impUid);
            purchaseVO.setMerchant_uid(merchantUid);
            purchaseVO.setAmount(amount);
            purchaseVO.setStatus(status);
            purchaseVO.setItem_num(itemNum);
            purchaseVO.setItem_name(itemName);
            purchaseVO.setBuyer_name(buyerName);
            purchaseVO.setBuyer_email(buyerEmail);

            try {
                purchaseService.insertPurchase(purchaseVO);
                mapJson.put("result", "success");
            } catch (Exception e) {
                log.error("결제 정보 저장 중 오류 발생", e);
                mapJson.put("result", "error");
                mapJson.put("message", "결제 정보 저장 중 오류가 발생했습니다. 관리자에게 문의하세요.");
            }
        }

        return mapJson;
    }
}