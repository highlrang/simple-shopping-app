package com.myproject.shopping.orderItem.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
public class OrderItemRequestDto {

    @NotNull(message = "상품 정보가 전달되지 않았습니다.")
    private Long itemId;

    @NotNull(message = "상품 수량이 전달되지 않았습니다.")
    private Integer quantity;

    @Builder
    public OrderItemRequestDto(Long itemId, Integer quantity){
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
