package com.myproject.shopping.orderItem.repository;

import com.myproject.shopping.orderItem.domain.OrderItem;
import com.myproject.shopping.orderItem.domain.dto.OrderItemResponseDto;
import lombok.RequiredArgsConstructor;
import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<OrderItemResponseDto> findDtoListByOrderId(Long orderId) {
        return em.createQuery(
                "SELECT new com.myproject.shopping.orderItem.domain.dto.OrderItemResponseDto(oi.id, i.id, i.name, oi.price, oi.quantity)"
                        + " FROM OrderItem oi"
                        + " JOIN Item i ON oi.item.id = i.id"
                        + " JOIN Order o ON oi.order.id = o.id"
                        + " WHERE o.id = :orderId"
                        + " ORDER BY oi.id DESC", OrderItemResponseDto.class
        )
                .setParameter("orderId", orderId)
                .getResultList();
    }


    @Override
    public List<OrderItem> findAllByOrderIds(List<Long> orderIds){
        return em.createQuery(
                "SELECT oi"
                + " FROM OrderItem oi"
                + " JOIN FETCH oi.order o"
                + " JOIN FETCH oi.item i"
                + " WHERE o.id in :orderIds", OrderItem.class)
                .setParameter("orderIds", orderIds)
                .getResultList();
    }
}