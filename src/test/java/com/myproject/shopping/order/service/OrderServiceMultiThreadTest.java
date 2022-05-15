package com.myproject.shopping.order.service;

import com.myproject.shopping.order.domain.Order;
import com.myproject.shopping.order.exception.SoldOutException;
import com.myproject.shopping.order.repository.OrderRepository;
import com.myproject.shopping.item.domain.Item;
import com.myproject.shopping.item.repository.ItemRepository;
import com.myproject.shopping.orderItem.domain.dto.OrderItemRequestDto;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceMultiThreadTest {

    @Mock
    static ItemRepository itemRepository;
    @Mock
    static OrderRepository orderRepository;
    @InjectMocks OrderServiceImpl orderService;

    private static Long itemId;
    private static Item givenItem;

    @BeforeClass
    public static void setUp(){
        itemId = 1L;
        givenItem = Item.builder()
                .id(itemId)
                .name("test item")
                .price(10000)
                .stock(100)
                .build();
    }

    public void save(int quantity) {
        /** when */
        try{
            List<OrderItemRequestDto> orderItemDtos = new ArrayList<>();
            orderItemDtos.add(
                OrderItemRequestDto.builder()
                        .itemId(itemId)
                        .quantity(quantity)
                        .build()
            );

            orderService.save(orderItemDtos);

            /** then */
            System.out.println("주문 완료되었습니다.");

        }catch (SoldOutException e){
            /** then */
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void multiThreadTest() throws Throwable {
        /** given */
        given(itemRepository.findById(anyLong()))
                .willReturn(Optional.of(givenItem));
        given(orderRepository.save(any(Order.class)))
                .willReturn(new Order());

        /** when */
        int trCnt = 20;
        ThreadTest1 thread1 = new ThreadTest1();
        ThreadTest2 thread2 = new ThreadTest2();
        ThreadTest3 thread3 = new ThreadTest3();
        TestRunnable[] trs = new TestRunnable[trCnt];

        for (int i=0; i<trCnt; i++){
            if(i % 2 == 0){
                trs[i] = thread1;
            }else if(i % 3 == 0){
                trs[i] = thread2;
            }else{
                trs[i] = thread3;
            }
        }

        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);

        mttr.runTestRunnables();

    }

    private class ThreadTest1 extends TestRunnable {
        @Override
        public void runTest() throws Throwable {
            save(15);
        }
    }

    private class ThreadTest2 extends TestRunnable {
        @Override
        public void runTest() throws Throwable {
            save(24);
        }
    }

    private class ThreadTest3 extends TestRunnable {
        @Override
        public void runTest() throws Throwable {
            save(30);
        }
    }

}