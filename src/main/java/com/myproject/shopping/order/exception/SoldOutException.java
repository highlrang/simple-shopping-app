package com.myproject.shopping.order.exception;

import static com.myproject.shopping.common.Constants.SOLD_OUT_MSG;

public class SoldOutException extends RuntimeException{

    public SoldOutException(){
        super(SOLD_OUT_MSG);
    }
}
