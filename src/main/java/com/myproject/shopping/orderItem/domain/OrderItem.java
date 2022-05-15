package com.myproject.shopping.orderItem.domain;

import com.myproject.shopping.item.domain.Item;
import com.myproject.shopping.order.domain.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity @Getter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @NotNull
    private int price;
    @NotNull
    private int quantity;

    private OrderItem(Item item, int price, int quantity){
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    public static OrderItem create(Item item, int price, int quantity){
        item.removeStock(quantity);
        return new OrderItem(item, price, quantity);
    }

    public void setOrder(Order order){
        this.order = order;
    }
    public void setItem(Item item){
        this.item = item;
    }
    public int getTotalPrice(){
        return price * quantity;
    }

}
