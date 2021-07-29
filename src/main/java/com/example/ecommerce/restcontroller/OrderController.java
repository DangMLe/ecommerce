package com.example.ecommerce.restcontroller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.ecommerce.DTO.OrderDTO;
import com.example.ecommerce.DTO.OrderDetailDTO;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.service.AccountService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AccountService accountService;

    
    private OrderDetailDTO convertToDTO(OrderDetail orderDetail){
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(orderDetail.getId());
        orderDetailDTO.setOrderid(orderDetail.getOrder().getId());
        orderDetailDTO.setProduct(orderDetail.getProduct().getName());
        orderDetailDTO.setPrice(orderDetail.getPrice());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        return orderDetailDTO;
    }
    
    private OrderDTO convertToDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setAccountName(order.getBuyer().getName());
        orderDTO.setDate(order.getDate());
        orderDTO.setOrderDetails(getOrderDetail(order.getId()));
        return orderDTO;
        
    }

    @GetMapping("/order/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    List<OrderDetailDTO> getOrderDetail(@PathVariable Long id){
        List<OrderDetail> orderDetails = orderService.getOrderDetail(orderService.getOrder(id));
        return orderDetails.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private OrderDetail convertToEntity(OrderDetailDTO orderDetailDTO){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(orderService.getOrder(orderDetailDTO.getOrderid()));
        orderDetail.setProduct(productService.getProductByName(orderDetailDTO.getProduct()));
        orderDetail.setPrice(orderDetailDTO.getPrice());
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        return orderDetail;
    }
    private Order convertToEntity(OrderDTO orderDTO){
        Order order = new Order();
        order.setBuyer(accountService.getAccountByName(orderDTO.getAccountName()));
        order.setDate(orderDTO.getDate());
        for (OrderDetail orderDetail : getOrderDetailDTOs(orderDTO)) {
            order.addOrderDetail(orderDetail);
        }
        return order;
    }
    List<OrderDetail> getOrderDetailDTOs(OrderDTO orderDTO){
        List<OrderDetailDTO> orderDetailDTOs = orderDTO.getOrderDetails();
        return orderDetailDTOs.stream().map(this::convertToEntity).collect(Collectors.toList());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    List<OrderDTO> getAllOrder(){
        List<Order> orders = orderService.getAllOrder(); 
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/account/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    List<OrderDTO> getAllOrderByAccount(@PathVariable Long id){
        List<Order> orders = orderService.getAllOrderByAccount(accountService.getAccount(id));
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    OrderDTO addOrder(@RequestBody OrderDTO orderDTO){
        orderDTO.setDate(new Date());;
        return convertToDTO(orderService.addOrder(convertToEntity(orderDTO)));
    }
    
    @PostMapping("order/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    OrderDetailDTO addOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO, @PathVariable Long id){
        return convertToDTO(orderService.addOrderDetail(convertToEntity(orderDetailDTO),id));
    }

    @PutMapping("order/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long id){
        orderDTO.setDate(new Date());
        Order order = convertToEntity(orderDTO);
        orderService.updateOrder(order, id);
    }
    
    @PutMapping("/orderDetail/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void updateOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO, @PathVariable Long id){
        OrderDetail orderDetail = convertToEntity(orderDetailDTO);
        orderService.updateOrderDetail(orderDetail, id);
    }

    @DeleteMapping("/order/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
    }

    @DeleteMapping("/orderDetail/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteOrderDetail(@PathVariable Long id){
        orderService.deleteOrderDetail(id);
    }
}
