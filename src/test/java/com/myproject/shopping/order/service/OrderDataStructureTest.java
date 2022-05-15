package com.myproject.shopping.order.service;

import com.myproject.shopping.item.domain.Item;
import com.myproject.shopping.orderItem.domain.OrderItem;
import com.myproject.shopping.orderItem.domain.dto.OrderItemRequestDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDataStructureTest {
    private static Long itemId = 1L;

    static class RequestByArrayThread extends Thread {
        RequestByArrayThread(){}
        @Override
        public void run(){
            try{
                for(int i=0; i<10; i++)
                    requestByArrayList();

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    static class RequestByLinkedThread extends Thread {
        RequestByLinkedThread(){}
        @Override
        public void run(){
            try{
                for(int i=0; i<10; i++)
                    requestByLinkedList();

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void requestByArrayList(){
        itemId++;
        /** request dto */
        List<OrderItemRequestDto> dtos = new ArrayList<>();
        dtos.add(OrderItemRequestDto.builder().itemId(itemId).quantity(1).build());
        if(itemId % 20 == 0) itemId--;
        dtos.add(OrderItemRequestDto.builder().itemId(itemId+1).quantity(2).build());
        dtos.add(OrderItemRequestDto.builder().itemId(itemId+2).quantity(3).build());


        /** list to set */
        OrderItemRequestDto prevDto = dtos.get(0);
        int length = dtos.size();
        for (int i=1; i<length; i++) {
            OrderItemRequestDto nowDto = dtos.get(i);

            if (!nowDto.getItemId().equals(prevDto.getItemId())){
                prevDto = nowDto;

            }else {
                prevDto.setQuantity(prevDto.getQuantity() + nowDto.getQuantity());
                dtos.remove(nowDto);
                length -= 1;
            }
        }

        /** dto to entity */
        Item item = Item.builder().stock(1000000000).build();
        List<OrderItem> orderItems = dtos.stream().map(
                dto -> OrderItem.create(item, item.getPrice(), dto.getQuantity())
        ).collect(Collectors.toList());
    }

    private static void requestByLinkedList(){
        /** request dto */
        List<OrderItemRequestDto> dtos = new LinkedList<>();
        dtos.add(OrderItemRequestDto.builder().itemId(itemId).quantity(1).build());
        if(itemId % 20 == 0) itemId--;
        dtos.add(OrderItemRequestDto.builder().itemId(itemId+1).quantity(2).build());
        dtos.add(OrderItemRequestDto.builder().itemId(itemId+2).quantity(3).build());


        /** list to set */
        OrderItemRequestDto prevDto = dtos.get(0);
        int length = dtos.size();
        for (int i=1; i<length; i++) {
            OrderItemRequestDto nowDto = dtos.get(i);

            if (!nowDto.getItemId().equals(prevDto.getItemId())){
                prevDto = nowDto;

            }else {
                prevDto.setQuantity(prevDto.getQuantity() + nowDto.getQuantity());
                dtos.remove(nowDto);
                length -= 1;
            }
        }

        /** dto to entity */
        Item item = Item.builder().stock(1000000000).build();
        List<OrderItem> orderItems = dtos.stream().map(
                dto -> OrderItem.create(item, item.getPrice(), dto.getQuantity())
        ).collect(Collectors.toList());
    }

    @Test
    public void compareTime(){
        RequestByArrayThread[] mtArray = new RequestByArrayThread[1000];
        for(int i=0; i<1000; i++){
            mtArray[i] = new RequestByArrayThread();
        }

        long startTime =  System.nanoTime();
        for (RequestByArrayThread thread : mtArray) {
            thread.start();
        }
        long endTime =  System.nanoTime();

        System.out.printf("\nArrayList 총 수행 시간 %.3f 초", ((double) endTime - startTime) / 1000000000);

        System.out.println("\n======================================================");

        RequestByLinkedThread[] mtLinked = new RequestByLinkedThread[1000];
        for(int i=0; i<1000; i++){
            mtLinked[i] = new RequestByLinkedThread();
        }

        startTime = System.nanoTime();
        for (RequestByLinkedThread thread : mtLinked) {
            thread.start();
        }
        endTime = System.nanoTime();

        System.out.printf("LinkedList 총 수행 시간 %.3f 초", ((double) endTime - startTime) / 1000000000);

        System.out.println("\n");

    }


}
