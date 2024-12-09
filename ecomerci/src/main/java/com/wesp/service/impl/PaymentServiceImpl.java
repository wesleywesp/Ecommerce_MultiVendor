package com.wesp.service.impl;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.wesp.domain.PaymentOrderStatus;
import com.wesp.infra.exception.PaymentException;
import com.wesp.model.Order;
import com.wesp.model.PaymentOrder;
import com.wesp.model.User;
import com.wesp.repository.OrderRepository;
import com.wesp.repository.PaymentOrderRepository;
import com.wesp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentOrderRepository paymentOrderRepository;
    private OrderRepository orderRepository;
//    private  String apikey="apikey";//mbawy payment
//    private String apiSecret= "apiSecret";//mbaway payment

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;
    @Override
    public PaymentOrder creatOrder(User user, Set<Order> orders) {
        BigDecimal amount = orders.stream().map(Order::getTotalSellerPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setAmount(amount);
        paymentOrder.setUser(user);
        paymentOrder.setOrder(orders);
        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(String orderId) {

        return paymentOrderRepository.findById(Long.valueOf(orderId)).orElseThrow(() -> new PaymentException("Payment Order not found"));
    }

    @Override
    public PaymentOrder getPaymentOrderBYPaymentId(String paymentId) {
        PaymentOrder paymentOrder = paymentOrderRepository.findByPaymentLinkId(paymentId);
        if (paymentOrder == null) {
            throw new PaymentException("Payment Order not found");
        }
        return paymentOrder;
    }

    @Override
    public Boolean ProceedPaymentOrder(PaymentOrder paymentOrder, String paymentId, String paymentlinkId) {
        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {
            //min 11:05 falta faze o pagamento via MBaway
            //MBaway mbpayment= new MBaway(apikey,apiSecret);

            //Payment payment = mbpayment.payments.fetch(paymentId);
            //String Status = payment.get("status");
            //if(Status.equals("captured")){
            //set<Order> orders = paymentOrder.getOrders();
            //for(Order order: orders)
            // order.setStatus(OrderStatus.CONFIRMED);
            // orderRepository.save(order);
            // }
            // paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            // paymentOrderRepository.save(paymentOrder);
           // return true;
            //}
            //paymentOrder.setStatus(PaymentOrderStatus.FAILED);
            //paymentOrderRepository.save(paymentOrder);
            //return false;
        }
        return false;
    }

    @Override
    public String createStrypePaymentLinkById(User user, BigDecimal amount, Long paymentId) {
        Stripe.apiKey = stripeSecretKey;

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do pagamento deve ser maior que zero.");
        }

        // Calcular tempo de expiração (30 minutos)
        long expiresAt = Instant.now().plus(30, ChronoUnit.MINUTES).getEpochSecond();

        // Criar parâmetros para a sessão
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.PAYPAL)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.KLARNA)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success/" + paymentId)
                .setCancelUrl("http://localhost:3000/payment-cancel/" + paymentId)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("eur")
                                .setUnitAmount(amount.multiply(BigDecimal.valueOf(100)).longValue()) // Corrigido
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("EcomerciMultiVender")
                                        .build())
                                .build())
                        .build())
                .setExpiresAt(expiresAt)
                .build();
        try {
            // Criar sessão de pagamento no Stripe
            Session session = Session.create(params);

            // Retornar a URL de pagamento
            return session.getUrl();
        } catch (Exception e) {
            throw new PaymentException("Erro ao criar o link de pagamento no Stripe." + e);
        }
    }


//    @Override
//    public PaymentLink createMBWayPaymentLink(User user, BigDecimal amount, Long paymentId) {
//        amount= amount.multiply(BigDecimal.valueOf(100));
//        try {
//            //MBaway mbpayment= new MBaway(apikey,apiSecret);
//            JSONObject paymentLinkRequest = new JSONObject();
//            paymentLinkRequest.put("amount", amount);
//            paymentLinkRequest.put("currency", "EUR");
//
//            JSONObject customer = new JSONObject();
//            customer.put("name", user.getName());
//            customer.put("email", user.getEmail());
//            paymentLinkRequest.put("customer", customer);
//
//
//            JSONObject notification = new JSONObject();
//            notification.put("email", true);
//            notification.put("sms", true);
//            paymentLinkRequest.put("notification", notification);
//
//            paymentLinkRequest.put("callback_url", "https://localhost:300/payment-success/"+paymentId);
//            paymentLinkRequest.put("callback_method", "GET");
//
//            PaymentLink paymentLink =
//
//        }
//        catch (Exception e){
//            throw new PaymentException("Error creating payment link");
//        }
//
//        return null;
//    }
}
