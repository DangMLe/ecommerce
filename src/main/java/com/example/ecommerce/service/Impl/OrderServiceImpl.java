package com.example.ecommerce.service.Impl;

import java.util.List;

import com.example.ecommerce.entity.Account;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.exception.OrderException;
import com.example.ecommerce.repository.OrderDetailRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @Override
    public Order addOrder(Order order) {
        // TODO Auto-generated method stub
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrder() {
        // TODO Auto-generated method stub
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getAllOrderByAccount(Account account) {
        // TODO Auto-generated method stub
        return orderRepository.findByAccount(account);
    }

    @Override
    public Order getOrder(Long id) {
        // TODO Auto-generated method stub
        return orderRepository.findById(id).orElseThrow(()-> new OrderException(id));
    }

    @Override
    public Order updateOrder(Order order, Long id) {
        // TODO Auto-generated method stub
        return orderRepository.findById(id).map(newOrder->{
            newOrder.setBuyer(order.getBuyer());
            newOrder.setDate(order.getDate());
            return newOrder;
        }).orElseThrow(()-> new OrderException(id));
    }

    @Override
    public void deleteOrder(Long id) {
        // TODO Auto-generated method stub
        orderRepository.deleteById(id);
    }

    
    @Override
    public OrderDetail addOrderDetail(OrderDetail orderDetail, Long id) {
        // TODO Auto-generated method stub
        Order order = orderRepository.findById(id).orElseThrow(()-> new OrderException(id));
        OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);
        order.getOrderDetails().add(orderDetail);
        return newOrderDetail;
    }

    @Override
    public OrderDetail updateOrderDetail(OrderDetail orderDetail, Long id) {
        // TODO Auto-generated method stub
        return orderDetailRepository.findById(id).map(newOrderDetail->{
            newOrderDetail.setOrder(orderDetail.getOrder());
            newOrderDetail.setProduct(orderDetail.getProduct());
            newOrderDetail.setPrice(orderDetail.getPrice());
            newOrderDetail.setQuantity(orderDetail.getQuantity());
            return newOrderDetail;
        }).orElseThrow(()-> new OrderException(id));
        
    }

    @Override
    public void deleteOrderDetail(Long id) {
        // TODO Auto-generated method stub
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> getOrderDetail(Order order) {
        // TODO Auto-generated method stub
        return orderDetailRepository.findAllByOrder(order);
    }
    
}
