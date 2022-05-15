package com.myproject.shopping.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponseDto<T> {
    private String status;
    private String message;
    private T object;

    public ApiResponseDto(String status, String message){
        this.status = status;
        this.message = message;
    }

    public ApiResponseDto(String status, T object){
        this.status = status;
        this.object = object;
    }
}
