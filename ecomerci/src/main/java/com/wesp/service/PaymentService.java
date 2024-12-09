package com.wesp.service;

import com.stripe.model.PaymentLink;
import com.wesp.model.Order;
import com.wesp.model.PaymentOrder;
import com.wesp.model.User;

import java.math.BigDecimal;
import java.util.Set;

public interface PaymentService {
    PaymentOrder creatOrder(User user, Set<Order> orders);
    PaymentOrder getPaymentOrderById(String orderId);
    PaymentOrder getPaymentOrderBYPaymentId(String paymentId);
    Boolean ProceedPaymentOrder(PaymentOrder paymentOrder,
                                 String paymentId,
                                 String paymentlinkId);


    String createStrypePaymentLinkById(User user, BigDecimal amount, Long paymentId);

//    PaymentLink createMBWayPaymentLink(User user, BigDecimal amount, Long paymentId);
}
