package kr.spring.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.cart.service.CartService;
import kr.spring.cart.vo.CartVO;
import kr.spring.goods.controller.GoodsController;
import kr.spring.goods.vo.GoodsVO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
    

    //자바빈 초기화
	@ModelAttribute
	public CartVO initCommand() {
		return new CartVO();
    }
	 /*===================================
     *          장바구니 등록
     *==================================*/
    @PostMapping("/insert")
    public String insertCart(@RequestParam("item_num") long itemNum,
                             @RequestParam("cart_quantity") long cartQuantity,
                             @RequestParam("mem_num") long memNum,
                             HttpServletRequest request,
                             Model model) throws IllegalStateException, IOException {

        CartVO cart = new CartVO();
        cart.setItem_num(itemNum);
        cart.setCart_quantity(cartQuantity);
        cart.setMem_num(memNum);
        
        cartService.insertCart(cart);
        
        model.addAttribute("message", "성공적으로 상품이 등록되었습니다.");
        model.addAttribute("uri", request.getContextPath() + "/goods/list");
        
        return "common/resultAlert";
    }
}