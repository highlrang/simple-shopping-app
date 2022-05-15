package com.myproject.shopping.orderItem.repository;

import com.myproject.shopping.orderItem.domain.OrderItem;
import com.myproject.shopping.orderItem.domain.dto.OrderItemResponseDto;

import java.util.List;

public interface OrderItemRepositoryCustom {

    List<OrderItemResponseDto> findDtoListByOrderId(Long id);
    List<OrderItem> findAllByOrderIds(List<Long> orderIds);
}
