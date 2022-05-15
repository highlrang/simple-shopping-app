package com.myproject.shopping.order.service;

import com.myproject.shopping.order.domain.Order;
import com.myproject.shopping.order.repository.OrderRepository;
import com.myproject.shopping.common.dto.PageDto;
import com.myproject.shopping.item.domain.Item;
import com.myproject.shopping.order.domain.dto.OrderResponseDto;
import com.myproject.shopping.orderItem.domain.OrderItem;
import com.myproject.shopping.orderItem.repository.OrderItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;
    @Mock OrderItemRepository orderItemRepository;
    @InjectMocks OrderServiceImpl orderService;

    @Test
    @DisplayName("주문 목록 더보기")
    public void list() {

        /** given */
        Item item1 = Item.builder()
                .name("test item 1")
                .price(10000)
                .stock(100)
                .build();
        Item item2 = Item.builder()
                .name("test item 2")
                .price(20000)
                .stock(200)
                .build();

        Order order1;
        Order order2;
        Order order3;

        OrderItem orderItem1 = OrderItem.create(item1, item1.getPrice(), 10);;
        OrderItem orderItem2 = OrderItem.create(item2, item2.getPrice(), 3);;
        order1 = Order.create(Arrays.asList(orderItem1, orderItem2));
        order1.setId(1L);

        OrderItem orderItem3 = OrderItem.create(item1, item1.getPrice(), 17);
        order2 = Order.create(Arrays.asList(orderItem3));
        order2.setId(2L);

        OrderItem orderItem4 = OrderItem.create(item1, item1.getPrice(), 21);
        order3 = Order.create(Arrays.asList(orderItem4));
        order3.setId(3L);

        List<OrderItem> orderItemList = Arrays.asList(orderItem1, orderItem2, orderItem3, orderItem4);
        List<Order> orderList = Arrays.asList(order1, order2, order3);


        int page = 0;
        int size = 2;
        int offset = page * size;

        List<Order> orderSubList = orderList.subList(offset, offset + size);
        given(orderRepository.findAllBy(any(Pageable.class)))
                .willReturn(orderSubList);

        List<Long> orderIds = orderSubList.stream().map(Order::getId).collect(Collectors.toList());
        given(orderItemRepository.findAllByOrderIds(anyList()))
                .willReturn(orderItemList.stream()
                        .filter(oi -> orderIds.contains(oi.getOrder().getId()))
                        .collect(Collectors.toList()));


        /** when */
        PageDto<List<OrderResponseDto>> result =
                (PageDto<List<OrderResponseDto>>) orderService.findAll(PageRequest.of(page, size));

        /** then */
        assertThat(result.getPage()).isEqualTo(page);
        List<Long> resultOrderIds = result.getContent().stream().map(OrderResponseDto::getId).collect(Collectors.toList());
        orderSubList.forEach(o -> assertTrue(resultOrderIds.contains(o.getId())));

    }
}
