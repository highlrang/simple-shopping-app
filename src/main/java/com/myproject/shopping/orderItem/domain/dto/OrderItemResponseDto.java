package com.myproject.shopping.orderItem.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.myproject.shopping.orderItem.domain.OrderItem;
import com.myproject.shopping.utils.formatter.CurrencyFormatter;
import lombok.Getter;

@Getter
public class OrderItemResponseDto {

    private Long id;

    private Long itemId;
    private String itemName;

    @JsonSerialize(using = CurrencyFormatter.class)
    private Integer price;

    private String quantity;

    @JsonSerialize(using = CurrencyFormatter.class)
    private Integer totalPrice;

    public OrderItemResponseDto(OrderItem orderItem){
        this.id = orderItem.getId();
        this.itemId = orderItem.getItem().getId();
        this.itemName = orderItem.getItem().getName();
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity() + "개";
        this.totalPrice = orderItem.getTotalPrice();
    }

    public OrderItemResponseDto(Long id, Long itemId, String itemName, int price, int quantity){
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity + "개";
        this.totalPrice = price * quantity;
    }
}
