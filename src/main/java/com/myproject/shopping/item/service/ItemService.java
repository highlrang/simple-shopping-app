package com.myproject.shopping.item.service;

import com.myproject.shopping.item.domain.dto.ItemResponseDto;
import com.myproject.shopping.common.dto.PageDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    List<ItemResponseDto> findAll();

    ItemResponseDto findById(Long id);

    PageDto<?> findMore(Pageable pageable);


}
