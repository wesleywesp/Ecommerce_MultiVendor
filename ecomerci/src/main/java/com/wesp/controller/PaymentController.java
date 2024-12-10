package com.wesp.controller;

import com.wesp.infra.exception.CustumerException;
import com.wesp.infra.exception.SellerException;
import com.wesp.model.*;
import com.wesp.response.ApiResponse;
import com.wesp.response.PaymentLinkResponse;
import com.wesp.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final SellerService sellerService;
    private final OrderService orderService;
    private final SellerReportService sellerReportService;
    private final TransactionService transactionService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<?> paymentSucessoHandler(@PathVariable String paymentId,
                                                   @RequestParam String paymentLinkId,
                                                   @RequestHeader String token) throws CustumerException, SellerException {
        User user = userService.findByJwtToken(token);
        PaymentLinkResponse paymentLinkResponse;
        PaymentOrder paymentOrder = paymentService.getPaymentOrderBYPaymentId(paymentId);
        boolean paymentSuccess = paymentService.ProceedPaymentOrder(paymentOrder, paymentId, paymentLinkId);

        if (paymentSuccess) {
            for (Order order : paymentOrder.getOrder()) {
                transactionService.createTransaction(order);
                Seller seller = sellerService.getSellerById(order.getSellerId());
                SellerReport sellerReport = sellerReportService.getSellerReport(seller);
                sellerReport.setTotalOrders(sellerReport.getTotalOrders() + 1);
                sellerReport.setTotalEarnings(sellerReport.getTotalEarnings().add(order.getTotalSellerPrice()));
                sellerReport.setTotalSales(sellerReport.getTotalSales().add(BigDecimal.valueOf(order.getOrderItems().size())));
                sellerReportService.updateSellerReport(sellerReport);
            }
        }
        ApiResponse res = new ApiResponse("Payment Success");
        return ResponseEntity.ok(res);
    }
}

