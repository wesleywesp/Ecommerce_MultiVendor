package com.wesp.service;

import com.wesp.domain.OrderStatus;
import com.wesp.model.*;

import java.util.List;
import java.util.Set;

public interface OrderService {
    Set<Order> createOrder(User user, Address shippingAddress, Cart cart);
    Order findOrderById(Long orderId);
    List<Order>userOrderHistory(Long userId);
    List<Order>SellerOrder(Long sellerId);
    Order updateOrderStatus(Long orderId, OrderStatus orderStatus);
    Order cancelOrder(Long orderId, User user);
    OrderItem getOrderItemById(Long orderItemId);

}
