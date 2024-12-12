package com.wesp.controller;

import com.wesp.infra.exception.CustumerException;
import com.wesp.model.Cart;
import com.wesp.model.Coupon;
import com.wesp.model.User;
import com.wesp.service.CartService;
import com.wesp.service.CouponService;
import com.wesp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class AdminCouponController {
    private final CouponService couponService;
    private final UserService userService;
    private final CartService cartService;

    @PostMapping("/apply")
    public ResponseEntity<Cart> applyCoupon(@RequestParam String apply,
                                            @RequestParam String code,
                                            @RequestParam BigDecimal orderValue,
                                            @RequestHeader("Authorization") String token) throws CustumerException {
        User user = userService.findByJwtToken(token);
        Cart cart;
        if(apply.equals("true")){
            cart = couponService.applyCoupon(code, orderValue, user);
        }else {
            cart = couponService.RemoveCoupon(code, user);
        }
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon){
        return ResponseEntity.ok(couponService.createCoupon(coupon));
    }

    @DeleteMapping("/admin/delete/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long couponId){
        couponService.deleteCoupon(couponId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/admin/all")
    public ResponseEntity<List<Coupon>> findAllCoupons(){
        return ResponseEntity.ok(couponService.findAllCoupons());
    }
}
