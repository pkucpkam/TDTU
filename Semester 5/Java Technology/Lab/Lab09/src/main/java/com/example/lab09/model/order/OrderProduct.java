package com.example.lab09.model.order;

import com.example.lab09.model.orderDetail.OrderDetail;
import com.example.lab09.model.product.Product;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "OrderProduct")
public class OrderProduct {
    @Id
    @Column(unique = true)
    private String id;

    private long totalSellingPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("id")
    @JsonManagedReference
    private List<OrderDetail> orderItems = new ArrayList<>();

    public void addProduct(Product product, int quantity) {
        OrderDetail orderItem = new OrderDetail(this, product, quantity);
        orderItems.add(orderItem);
    }

    public void removeProduct(Product product) {
        orderItems.removeIf(orderItem -> orderItem.getProduct().equals(product));
    }

}
