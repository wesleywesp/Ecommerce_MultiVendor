package com.wesp.controller;

import com.stripe.model.PaymentLink;
import com.wesp.domain.PaymentMethod;
import com.wesp.infra.exception.CustumerException;
import com.wesp.infra.exception.SellerException;
import com.wesp.model.*;

import com.wesp.response.PaymentLinkResponse;
import com.wesp.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService  userService;
    private final CartService cartService;
    private final SellerService sellerService;
    private final SellerReportService sellerReportService;
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentLinkResponse> createOrderHandler(
            @RequestBody @Valid Address address,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String token) throws CustumerException {
        User user = userService.findByJwtToken(token);
        Cart cart = cartService.findUserCard(user);
        Set<Order> orders = orderService.createOrder(user, address, cart);

        PaymentOrder paymentOrder =paymentService.creatOrder(user, orders);
//
        PaymentLinkResponse response = new PaymentLinkResponse();

            String paymentUrl = paymentService.createStrypePaymentLinkById(user, paymentOrder.getAmount()
                    ,paymentOrder.getId());
            response.setPayment_link_url(paymentUrl);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization") String token) throws CustumerException {
        User user = userService.findByJwtToken(token);
        return ResponseEntity.ok(orderService.userOrderHistory(user.getId()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String token) throws CustumerException {
        User user = userService.findByJwtToken(token);
        return ResponseEntity.ok(orderService.findOrderById(orderId));
    }

    @GetMapping("/item/{orderItemId}")
    public ResponseEntity<OrderItem> findOrderItemById(@PathVariable Long orderItemId,
                                                       @RequestHeader("Authorization") String token) throws CustumerException {
        User user = userService.findByJwtToken(token);
        return ResponseEntity.ok(orderService.getOrderItemById(orderItemId));
    }

    @GetMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String token) throws CustumerException, SellerException {
        User user = userService.findByJwtToken(token);
        Order order = orderService.findOrderById(orderId);
        Seller seller = sellerService.getSellerById(order.getSellerId());
        SellerReport report= sellerReportService.getSellerReport(seller);

        report.setCancelledOrders(report.getCancelledOrders()+1);
        report.setTotalRefunds(report.getTotalRefunds().add(order.getTotalSellerPrice()));
        sellerReportService.updateSellerReport(report);
        return ResponseEntity.ok(order);
    }
}
