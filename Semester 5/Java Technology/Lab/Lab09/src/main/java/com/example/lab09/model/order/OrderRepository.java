package com.example.lab09.model.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderProduct,String> {
    List<OrderProduct> findAll();
}
