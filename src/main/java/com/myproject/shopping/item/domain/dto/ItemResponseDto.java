package com.myproject.shopping.item.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.myproject.shopping.utils.formatter.CurrencyFormatter;
import com.myproject.shopping.item.domain.Item;
import lombok.Getter;

@Getter
public class ItemResponseDto {

    private Long id;

    private String name;

    @JsonSerialize(using = CurrencyFormatter.class)
    private Integer price;

    private Integer stock;

    public ItemResponseDto(Item item){
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stock = item.getStock();
    }

    @Override
    public String toString() {
        return id + "   " +
               name + "    " +
               price + "    " +
               stock;
    }
}
