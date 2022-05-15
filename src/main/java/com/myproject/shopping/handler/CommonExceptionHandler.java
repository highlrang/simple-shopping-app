package com.myproject.shopping.handler;

import com.myproject.shopping.order.exception.SoldOutException;
import com.myproject.shopping.common.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.myproject.shopping.common.Constants.ERROR;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> notFound(IllegalArgumentException e){
        return new ResponseEntity<>(
                new ApiResponseDto<>(ERROR, e.getMessage())
                , HttpStatus.OK);
    }

    @ExceptionHandler(SoldOutException.class)
    public ResponseEntity<?> soldOut(SoldOutException e){
        return new ResponseEntity<>(
                new ApiResponseDto<>(ERROR, e.getMessage())
                , HttpStatus.OK);
    }
}
