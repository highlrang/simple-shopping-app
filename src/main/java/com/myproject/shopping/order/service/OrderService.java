package com.myproject.shopping.order.service;

import com.myproject.shopping.common.dto.PageDto;
import com.myproject.shopping.order.domain.dto.OrderResponseDto;
import com.myproject.shopping.orderItem.domain.dto.OrderItemRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Long save(List<OrderItemRequestDto> dtos);

    OrderResponseDto findById(Long id);

    PageDto<?> findAll(Pageable pageable);

}
