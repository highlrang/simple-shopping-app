package com.myproject.shopping.item.service;

import com.myproject.shopping.item.domain.Item;
import com.myproject.shopping.item.domain.dto.ItemResponseDto;
import com.myproject.shopping.item.repository.ItemRepository;
import com.myproject.shopping.common.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.myproject.shopping.common.Constants.ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    @Override
    public List<ItemResponseDto> findAll() {
        return itemRepository.findAllByOrderByIdDesc()
                .stream()
                .map(ItemResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponseDto findById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ITEM_NOT_FOUND));
        return new ItemResponseDto(item);
    }


    @Override
    public PageDto<?> findMore(Pageable pageable) {
        return new PageDto<>(pageable.getPageNumber(),
                itemRepository.findAllBy(pageable).stream()
                        .map(ItemResponseDto::new)
                        .collect(Collectors.toList())
        );
    }
}
