package com.wesp.service.impl;

import com.wesp.infra.exception.CouponException;
import com.wesp.model.Cart;
import com.wesp.model.Coupon;
import com.wesp.model.User;
import com.wesp.repository.CartRepository;
import com.wesp.repository.CouponRepository;
import com.wesp.repository.UserRepository;
import com.wesp.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponSeviceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;


    @Override
    public Cart applyCoupon(String code, BigDecimal orderValue, User user) {
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> new CouponException("Coupon not valid"));
        Cart cart = cartRepository.findByUserId(user.getId());

        if(user.getUsedCoupons().contains(coupon)){
            throw new CouponException("Coupon already used");
        }
        if(orderValue.compareTo(coupon.getMinimumOrderAmount()) < 0) {
            throw new CouponException("Minimum order value not met "+ coupon.getMinimumOrderAmount());
        }
        if(coupon.isActive() &&
                LocalDate.now().isAfter(coupon.getStartDate())
                && LocalDate.now().isBefore(coupon.getExpirationDate())){
            user.getUsedCoupons().add(coupon);
            userRepository.save(user);

            BigDecimal discount = orderValue.multiply(coupon.getDiscountPercentage());
            cart.setTotalSellingPrice(orderValue.subtract(discount));
            cart.setCouponCode(code);
            cartRepository.save(cart);
            return cart;
        }
        throw new CouponException("Coupon not valid");
    }

    @Override
    public Cart RemoveCoupon(String code, User user) {
        return null;
    }

    @Override
    public Coupon findCouponById(Long couponId) {
        return null;
    }

    @Override
    public Coupon createCoupon(Coupon coupon) {
        return null;
    }

    @Override
    public List<Coupon> findAllCoupons() {
        return List.of();
    }

    @Override
    public void deleteCoupon(Long couponId) {

    }
}