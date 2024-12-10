package com.wesp.service;

import com.stripe.model.tax.Registration;
import com.wesp.model.Cart;
import com.wesp.model.Coupon;
import com.wesp.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface CouponService {
    Cart applyCoupon(String code, BigDecimal orderValue, User user);
    Cart RemoveCoupon(String code,User user);

    Coupon findCouponById(Long couponId);
    Coupon createCoupon(Coupon coupon);
    List<Coupon> findAllCoupons();
    void deleteCoupon(Long couponId);
}
