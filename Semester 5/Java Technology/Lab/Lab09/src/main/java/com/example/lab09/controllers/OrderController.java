package com.example.lab09.controllers;


import com.example.lab09.model.order.OrderProduct;
import com.example.lab09.model.order.OrderService;
import com.example.lab09.model.orderDetail.OrderDetail;
import com.example.lab09.model.orderDetail.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService services;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("")
    public List<OrderProduct> getAll(){
        return services.getAll();
    }

    @PostMapping(value = "")
    public OrderProduct add(@RequestParam("id") String id){
        OrderProduct orderProduct = OrderProduct.builder().id(id).build();
        services.addOrder(orderProduct);
        return orderProduct;
    }

    @PutMapping(value = "/{id}")
    public OrderProduct updateOrder(@PathVariable("id") String id, @ModelAttribute OrderDetail orderDetail){
        services.updateOrder(id, orderDetail);
        orderDetailService.addOrderDetail(orderDetail);
        return services.findById(id);
    }

    @GetMapping(value = "/{id}")
    public OrderProduct getOrder(@PathVariable("id") String id){
        return services.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public String deleteOrder(@PathVariable("id") String id){
        OrderProduct orderProduct = services.findById(id);
        if(services.deleteOrder(id)){
            return "Delete Successfully";
        }
        return "Failed to Delete";
    }
}
