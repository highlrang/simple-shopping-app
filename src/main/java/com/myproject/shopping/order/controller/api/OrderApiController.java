package com.myproject.shopping.order.controller.api;

import com.myproject.shopping.common.dto.ApiResponseDto;
import com.myproject.shopping.order.service.OrderService;
import com.myproject.shopping.orderItem.domain.dto.OrderItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.myproject.shopping.common.Constants.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody List<OrderItemRequestDto> dtoList){
        Long id = orderService.save(dtoList);
        return new ResponseEntity<>(
                new ApiResponseDto<>(SUCCESS, id)
                , HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<?> findList(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(
                new ApiResponseDto<>(SUCCESS, orderService.findAll(pageable)),
                HttpStatus.OK
        );
    }


}
