package com.myproject.shopping.order.repository;

import com.myproject.shopping.order.domain.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllBy(Pageable pageable);
}
