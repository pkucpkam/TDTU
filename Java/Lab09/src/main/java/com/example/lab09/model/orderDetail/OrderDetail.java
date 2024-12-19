package com.example.lab09.model.orderDetail;

import com.example.lab09.model.order.OrderProduct;
import com.example.lab09.model.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private OrderProduct order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    private int quantity;

    public OrderDetail(OrderProduct orderProduct, Product product, int quantity) {
        this.order = orderProduct;
        this.product = product;
        this.quantity = quantity;
    }
}
