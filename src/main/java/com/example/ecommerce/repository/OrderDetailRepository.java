package com.example.ecommerce.repository;

import java.util.List;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findAllByOrder(Order order);
    
}