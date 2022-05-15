package com.myproject.shopping.order.service;

import com.myproject.shopping.order.repository.OrderRepository;
import com.myproject.shopping.common.dto.PageDto;
import com.myproject.shopping.item.domain.Item;
import com.myproject.shopping.item.repository.ItemRepository;
import com.myproject.shopping.order.domain.Order;
import com.myproject.shopping.order.domain.dto.OrderResponseDto;
import com.myproject.shopping.orderItem.domain.OrderItem;
import com.myproject.shopping.orderItem.domain.dto.OrderItemRequestDto;
import com.myproject.shopping.orderItem.domain.dto.OrderItemResponseDto;
import com.myproject.shopping.orderItem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.myproject.shopping.common.Constants.ITEM_NOT_FOUND;
import static com.myproject.shopping.common.Constants.ORDER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;


    @Transactional
    @Override
    public Long save(List<OrderItemRequestDto> dtos) {

        /** 같은 상품 요청은 하나의 요청으로 합치기 */
        dtos = mergeQuantity(dtos);

        /** dto to entity */
        List<OrderItem> orderItems = dtos.stream().map(
                dto -> {
                    Item item = itemRepository.findById(dto.getItemId())
                            .orElseThrow(() -> new IllegalArgumentException(ITEM_NOT_FOUND));

                    return OrderItem.create(item, item.getPrice(), dto.getQuantity());
                }
        ).collect(Collectors.toList());

        Order order = orderRepository.save(Order.create(orderItems));
        return order.getId();
    }

    private List<OrderItemRequestDto> mergeQuantity(List<OrderItemRequestDto> dtos) {
        OrderItemRequestDto prevDto = dtos.get(0);
        int length = dtos.size();

        for (int i=1; i<length; i++) {
            OrderItemRequestDto nowDto = dtos.get(i);

            if (nowDto.getItemId().equals(prevDto.getItemId())) {
                nowDto.setQuantity(prevDto.getQuantity() + nowDto.getQuantity());
                dtos.remove(prevDto);
                length -= 1;
            }

            prevDto = nowDto;
        }

        return dtos;
    }


    @Override
    public OrderResponseDto findById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException(ORDER_NOT_FOUND));
        OrderResponseDto orderResponseDto = new OrderResponseDto(order);

        List<OrderItemResponseDto> orderItemDtos = orderItemRepository.findDtoListByOrderId(orderId);
        orderResponseDto.setOrderItems(orderItemDtos);
        return orderResponseDto;
    }



    /**
     * REST API에서 사용하는 주문 목록
     * 보통은 사용자를 기준으로 호출 findAllByUser
     * 사용자 모델이 없기 때문에 모든 주문 목록 호출
     */
    @Override
    public PageDto<?> findAll(Pageable pageable) {

        // order 목록 호출
        List<OrderResponseDto> orderList = orderRepository.findAllBy(pageable).stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
        List<Long> orderIds = orderList.stream()
                .map(OrderResponseDto::getId)
                .collect(Collectors.toList());

        // order item 목록 호출
        List<OrderItem> orderItemList = orderItemRepository.findAllByOrderIds(orderIds);

        // 주문 id로 grouping
        Map<Long, List<OrderItem>> mapByOrderId = orderItemList.stream()
                .collect(Collectors.groupingBy(o -> o.getOrder().getId()));

        // 각 주문 id에 해당하는 orderItem을 매핑
        orderList.forEach(o -> {
            List<OrderItemResponseDto> dtoList = mapByOrderId.get(o.getId()).stream()
                    .map(OrderItemResponseDto::new)
                    .collect(Collectors.toList());

            o.setOrderItems(dtoList);
        });

        return new PageDto<>(pageable.getPageNumber(), orderList);
    }
}
