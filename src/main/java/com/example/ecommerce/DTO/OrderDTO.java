package com.example.ecommerce.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    private Long id;
    private String accountName;
    private List<OrderDetailDTO> orderDetails = new ArrayList<>();
    private LocalDate date;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }
    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
}
