package com.myproject.shopping.item.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.shopping.common.dto.ApiResponseDto;
import com.myproject.shopping.common.dto.PageDto;
import com.myproject.shopping.item.domain.Item;
import com.myproject.shopping.item.domain.dto.ItemResponseDto;
import com.myproject.shopping.item.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.myproject.shopping.common.Constants.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ItemApiControllerTest {

    @MockBean ItemRepository itemRepository;
    @Autowired MockMvc mockMvc;

    @Test @DisplayName("상품 목록 더보기")
    void findList() throws Exception {

        List<Item> itemList = new ArrayList<>();
        for(int i=0; i<15; i++){
            itemList.add(Item.builder()
                    .id((long) i)
                    .name("상품 " + i)
                    .price(10000)
                    .stock(100)
                    .build()
            );
        }

        int page = 1;
        int size = 5;
        int offset = page * size;

        List<Item> itemSubList = itemList.subList(offset, offset + size);
        given(itemRepository.findAllBy(any(Pageable.class)))
                .willReturn(itemSubList);

        ObjectMapper om = new ObjectMapper();
        String content = om.writeValueAsString(
                new ApiResponseDto<>(SUCCESS,
                        new PageDto<>(page, itemSubList.stream()
                                                        .map(ItemResponseDto::new)
                                                        .collect(Collectors.toList()))
                )
        );

        mockMvc.perform(get("/api/item")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .accept("application/json;charset=utf-8")

        )
                .andExpect(status().isOk())
                .andExpect(content().json(content))
                .andDo(print());

    }
}