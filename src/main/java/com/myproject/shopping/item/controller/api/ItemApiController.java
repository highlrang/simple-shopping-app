package com.myproject.shopping.item.controller.api;

import com.myproject.shopping.common.dto.ApiResponseDto;
import com.myproject.shopping.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.myproject.shopping.common.Constants.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemApiController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<?> findList(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return new ResponseEntity<>(
                new ApiResponseDto<>(
                        SUCCESS,
                        itemService.findMore(pageable)
                ),
                HttpStatus.OK
        );
    }

}
