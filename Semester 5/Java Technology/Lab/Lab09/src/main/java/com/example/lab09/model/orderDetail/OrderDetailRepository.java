package com.example.lab09.model.orderDetail;

import com.example.lab09.model.order.OrderProduct;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrderDetail,Long> {
    List<OrderDetail> findAll();
    @Modifying
    @Query("DELETE FROM OrderDetail od WHERE od.order.id = :order_id")
    void deleteByOrder_Id(@Param("order_id") String order_id);


    List<OrderDetail> findAllByOrder(OrderProduct orderProduct);
    void deleteByOrder(OrderProduct order);
}
