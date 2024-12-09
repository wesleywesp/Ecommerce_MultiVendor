package com.wesp.service.impl;

import com.wesp.domain.OrderStatus;
import com.wesp.domain.PaymentStatus;
import com.wesp.infra.exception.OrderException;
import com.wesp.model.*;
import com.wesp.repository.AddressRepository;
import com.wesp.repository.OrderItemRepository;
import com.wesp.repository.OrderRepository;
import com.wesp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) {
        if(!user.getAddresses().contains(shippingAddress)) {
            user.getAddresses().add(shippingAddress);
        }
        Address address= addressRepository.save(shippingAddress);
        Map<Long, List<CartItem>> itemsBySeller = cart.getCarItems()
                .stream()
                .collect(Collectors.groupingBy(cartItem -> cartItem.getProduct()
                .getSeller().getId()));
        Set<Order>orders = new HashSet<>();
        for(Map.Entry<Long, List<CartItem>> entry:itemsBySeller.entrySet()){

            Long sellerId = entry.getKey();

            List<CartItem> items = entry.getValue();

            BigDecimal totalOrderPrice = items.stream()
                    .map(CartItem::getSellingPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            int totalItem = items.stream()
                    .mapToInt(CartItem::getQuantity)
                    .sum();
            Order createdOrder = new Order();
            createdOrder.setUser(user);
            createdOrder.setSellerId(sellerId);
            createdOrder.setShippingAddress(address);
            createdOrder.setTotalMrpPrice(totalOrderPrice);
            createdOrder.setTotalSellerPrice(totalOrderPrice);
            createdOrder.setTotalItem(totalItem);
            createdOrder.setOrderStatus(OrderStatus.PLACED);
            createdOrder.setPaymentStatus(PaymentStatus.PENDING);

            Order savedOrder = orderRepository.save(createdOrder);
            orders.add(savedOrder);
            List<OrderItem> orderItems = new ArrayList<>();
            for(CartItem cartItem:items){
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setMrpPrice(cartItem.getMrpPrice());
                orderItem.setSize(cartItem.getSize());
                orderItem.setUserId(cartItem.getUserId());
                orderItem.setSellingPrice(cartItem.getSellingPrice());

                savedOrder.getOrderItems().add(orderItem);

                OrderItem savedOrderItem = orderItemRepository.save(orderItem);
                orderItems.add(savedOrderItem);
            }
        }

        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(()-> new OrderException("Order not found"));
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> SellerOrder(Long sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderException("Order not found"));
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(Long orderId, User user) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderException("Order not found"));
        if(!order.getUser().getId().equals(user.getId())){
            throw new OrderException("Order not found");
        }
        if(order.getOrderStatus().equals(OrderStatus.CANCELLED)){
            throw new OrderException("Order already cancelled");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public OrderItem getOrderItemById(Long orderItemId) {

        return orderItemRepository.findById(orderItemId).orElseThrow(()-> new OrderException("Order Item not exist"));
    }
}
