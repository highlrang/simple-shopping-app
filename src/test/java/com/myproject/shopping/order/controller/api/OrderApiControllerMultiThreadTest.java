package com.myproject.shopping.order.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.shopping.order.repository.OrderRepository;
import com.myproject.shopping.item.domain.Item;
import com.myproject.shopping.item.repository.ItemRepository;
import com.myproject.shopping.order.domain.Order;
import com.myproject.shopping.orderItem.domain.dto.OrderItemRequestDto;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.myproject.shopping.common.Constants.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderApiControllerMultiThreadTest {

    @MockBean
    OrderRepository orderRepository;
    @MockBean ItemRepository itemRepository;
    @Autowired MockMvc mockMvc;
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

    public void save() throws Exception {
        given(itemRepository.findById(anyLong()))
                .willReturn(Optional.of(givenItem));

        given(orderRepository.save(any()))
                .willReturn(new Order());

        OrderItemRequestDto orderItemRequestDto = OrderItemRequestDto.builder()
                .itemId(itemId)
                .quantity(15)
                .build();
        List<OrderItemRequestDto> orderItemList = Arrays.asList(orderItemRequestDto);
        ObjectMapper om = new ObjectMapper();
        String content = om.writeValueAsString(orderItemList);

        MockHttpServletResponse response = mockMvc.perform(
                post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept("application/json;charset=utf8;")
        )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String result = response.getContentAsString();

        if(result.contains(ERROR)) {
            assertTrue(result.contains(SOLD_OUT_MSG));
            System.out.println(SOLD_OUT_MSG);
        }else{
            assertTrue(result.contains(SUCCESS));
            System.out.println("주문이 완료되었습니다.");
        }
    }

    @Test
    public void multiThreadTest() throws Throwable {
        TestRunnable[] trs = new TestRunnable[20];
        for(int i=0; i<20; i++){
            trs[i] = new ThreadTest();
        }

        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        mttr.runTestRunnables();

    }

    class ThreadTest extends TestRunnable{

        @Override
        public void runTest() throws Throwable {
            save();
        }
    }
}


