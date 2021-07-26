package com.example.ecommerce.DTO;

public class OrderDetailDTO {
    private Long id;
    private int quantity;
    private Long price;
    private String product;
    private Long orderid;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public Long getOrderid() {
        return orderid;
    }
    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }
    
}
