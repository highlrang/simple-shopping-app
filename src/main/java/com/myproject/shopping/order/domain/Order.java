package com.myproject.shopping.order.domain;

import com.myproject.shopping.orderItem.domain.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.myproject.shopping.common.Constants.DELIVERY_FEE;

@Entity @Getter
@Table(name = "ORDERS")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> orderItemList = new ArrayList<>();

    @NotNull
    private int totalPrice;
    @NotNull
    private int paymentAmount;
    @NotNull
    private int deliveryFee;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public void addOrderItem(OrderItem orderItem){
        this.orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order create(List<OrderItem> orderItems){
        Order order = new Order();
        orderItems.forEach(order::addOrderItem);
        order.calcPrice();
        return order;
    }

    public void calcPrice(){
        this.totalPrice = orderItemList.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
        this.deliveryFee = totalPrice < 50000 ? DELIVERY_FEE : 0;
        this.paymentAmount = totalPrice + deliveryFee;
    }

}
