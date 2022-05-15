package com.myproject.shopping.common.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PageDto<T extends List<?>> {

    /** 더보기(다음 페이지 요청) 기능을 위한 페이지 정보 */
    private int page;

    private T content;

    public PageDto(int page, T content){
        this.page = page;
        this.content = content;
    }
}
