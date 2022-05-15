package com.myproject.shopping.order.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.myproject.shopping.order.domain.Order;
import com.myproject.shopping.utils.formatter.CurrencyFormatter;
import com.myproject.shopping.orderItem.domain.dto.OrderItemResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderResponseDto {

    private Long id;

    private List<OrderItemResponseDto> orderItems;

    @JsonSerialize(using = CurrencyFormatter.class)
    private Integer totalPrice;

    @JsonSerialize(using = CurrencyFormatter.class)
    private Integer deliveryFee;

    @JsonSerialize(using = CurrencyFormatter.class)
    private Integer paymentAmount;

    public OrderResponseDto(Order order){
        this.id = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.deliveryFee = order.getDeliveryFee();
        this.paymentAmount = order.getPaymentAmount();
    }

    public void setOrderItems(List<OrderItemResponseDto> orderItems){
        this.orderItems = orderItems;
    }
}
