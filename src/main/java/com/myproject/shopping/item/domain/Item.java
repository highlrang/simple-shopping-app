package com.myproject.shopping.item.domain;

import com.myproject.shopping.order.exception.SoldOutException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Slf4j
@Entity @Getter
@NoArgsConstructor
public class Item {

    @Id
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private int price;

    @NotNull
    private int stock;

    @Builder
    public Item(Long id, String name, int price, int stock){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void removeStock(int quantity){
        int changedStock = this.stock - quantity;
        if(changedStock < 0)
            throw new SoldOutException();

        this.stock = changedStock;
    }
}
