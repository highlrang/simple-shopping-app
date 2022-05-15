package com.myproject.shopping.orderItem.repository;

import com.myproject.shopping.orderItem.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, OrderItemRepositoryCustom {


}
