package com.example.ecommerce.service;

import java.util.List;

import com.example.ecommerce.entity.Account;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderDetail;

import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Order addOrder(Order order);
    List<Order> getAllOrder();
    List<Order> getAllOrderByAccount(Account account);
    Order getOrder(Long id);
    Order updateOrder(Order order, Long id);
    void deleteOrder(Long id);
    List<OrderDetail> getOrderDetail(Order order);
    OrderDetail addOrderDetail(OrderDetail orderDetail, Long id);
    OrderDetail updateOrderDetail(OrderDetail orderDetail, Long id);
    void deleteOrderDetail(Long id);

}
