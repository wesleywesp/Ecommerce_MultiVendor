package com.wesp.controller;

import com.wesp.domain.OrderStatus;
import com.wesp.infra.exception.SellerException;
import com.wesp.model.Order;
import com.wesp.model.Seller;
import com.wesp.model.SellerReport;
import com.wesp.service.OrderService;
import com.wesp.service.SellerReportService;
import com.wesp.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller/order")
public class OrderSellerController {
    private final OrderService orderService;
    private final SellerService sellerService;
    private final SellerReportService sellerReportService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrdersHandler(@RequestHeader("Authorization") String token) throws SellerException {
        Seller seller = sellerService.getSellerProfile(token);
        SellerReport sellerReport = sellerReportService.getSellerReport(seller);
        List<Order> orders = orderService.SellerOrder(seller.getId());
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/{orderId}/status/{orderstatus}")
    public ResponseEntity<Order> updateOrderStatusHandler(@PathVariable Long orderId,
                                                          @PathVariable OrderStatus orderstatus,
                                                          @RequestHeader("Authorization") String token) throws SellerException {

        Order order = orderService.updateOrderStatus(orderId, orderstatus);
        return ResponseEntity.ok(order);
    }

}
