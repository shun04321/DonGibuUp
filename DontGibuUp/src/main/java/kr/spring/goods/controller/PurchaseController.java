package kr.spring.goods.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import kr.spring.goods.service.PurchaseService;
import kr.spring.goods.vo.PurchaseVO;
import kr.spring.member.vo.MemberVO;

@RestController
@RequestMapping("/goods")  // 이 부분을 추가합니다.
public class PurchaseController {
	private IamportClient impClient;

	@Autowired
	private PurchaseService purchaseService;
	 
    @Value("${iamport.apiKey}")
    private String apiKey;

    @Value("${iamport.secretKey}")
    private String secretKey;

    @PostConstruct
    public void initImp() {
        this.impClient = new IamportClient(apiKey, secretKey);
    }
    
    
    @GetMapping("/purchase")
    public String getPurchasePage(@RequestParam(value = "imp_uid", required = false) String impUid, Model model) {
        model.addAttribute("imp_uid", impUid);
        return "goods/purchase"; // GET 요청에 대해 goods/purchase.jsp 파일을 반환합니다.
    }

    @PostMapping("/purchase")
    public String purchasePage(@RequestParam("imp_uid") String impUid, Model model) {
        model.addAttribute("imp_uid", impUid);
        return "goods/purchase"; // POST 요청에 대해 goods/purchase.jsp 파일을 반환합니다.
    }

    
    @GetMapping("/purchase/refund")
    public String refundPage() {
        return "goods/refund"; 
    }
    
    
    @PostMapping("/purchase/paymentVerify/{imp_uid}")
    @ResponseBody
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid, HttpSession session, HttpServletRequest request)
            throws Exception {
        IamportResponse<Payment> paymentResponse = impClient.paymentByImpUid(imp_uid);
        Payment payment = paymentResponse.getResponse();
        long paidAmount = payment.getAmount().longValue();

        // 예제: 세션에 저장된 결제 금액과 비교하여 결제 금액 검증
        Long expectedAmount = (Long) session.getAttribute("expectedAmount");
        if (expectedAmount == null || expectedAmount != paidAmount) {
            throw new Exception("결제 금액이 일치하지 않습니다.");
        }

        return paymentResponse;
    }

    @PostMapping("/purchase/payAndEnroll")
    @ResponseBody
    public Map<String, String> savePurchaseInfo(@RequestBody Map<String, Object> data, HttpSession session, HttpServletRequest request)
            throws Exception {
        String odImpUid = (String) data.get("od_imp_uid");
        int payPrice = (Integer) data.get("pay_price");
        int payStatus = (Integer) data.get("pay_status");

        Map<String, String> mapJson = new HashMap<>();

        MemberVO member = (MemberVO) session.getAttribute("user");

        if (member == null) {
            mapJson.put("result", "logout");
        } else {
            PurchaseVO purchaseVO = new PurchaseVO();
            purchaseVO.setMemNum(member.getMem_num());
            purchaseVO.setPayPrice(payPrice);
            purchaseVO.setOdImpUid(odImpUid);

            try {
                purchaseService.insertPurchase(purchaseVO);
                mapJson.put("result", "success");
            } catch (Exception e) {
                mapJson.put("result", "error");
                mapJson.put("message", "Purchase information saving failed.");
            }
        }

        return mapJson;
    }


    @PostMapping("/purchase/refund/{imp_uid}")
    @ResponseBody
    public Map<String, String> refundPayment(@PathVariable String imp_uid) {
        Map<String, String> mapJson = new HashMap<>();

        try {
        	 CancelData cancelData = new CancelData(imp_uid, true);
        	 IamportResponse<Payment> cancelResponse = impClient.cancelPaymentByImpUid(cancelData);
             if (cancelResponse.getResponse() != null && "cancelled".equals(cancelResponse.getResponse().getStatus())) {
                 mapJson.put("result", "success");
             } else {
                 mapJson.put("result", "failed");
             }
        } catch (Exception e) {
            mapJson.put("result", "error");
            mapJson.put("message", e.getMessage());
        }

        return mapJson;
    }
}