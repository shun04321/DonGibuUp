package kr.spring.goods.controller;

import kr.spring.goods.service.PurchaseService;
import kr.spring.goods.vo.PurchaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class adminController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/admin/purchaseList")
    public String getPurchaseList(Model model) {
        List<PurchaseVO> purchaseList = purchaseService.getAllPurchases();
        model.addAttribute("purchaseList", purchaseList);
        return "goods/adminPurchase";
    }

    @PostMapping("/admin/updateDeliveryStatus")
    public String updateDeliveryStatus(@RequestParam("purchaseNum") int purchaseNum, @RequestParam("deliveryStatus") String deliveryStatus) {
        purchaseService.updateDeliveryStatus(purchaseNum, deliveryStatus);
        return "redirect:/admin/purchaseList";
}
}