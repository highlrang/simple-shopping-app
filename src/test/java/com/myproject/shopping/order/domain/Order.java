package com.myproject.shopping.order.domain;

import com.myproject.shopping.orderItem.domain.OrderItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.myproject.shopping.common.Constants.DELIVERY_FEE;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    private Long id;
    private int totalPrice;
    private int deliveryFee;
    private int paymentAmount;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> orderItemList = new ArrayList<>();

    public Order(){}

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

    /** getter setter */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
