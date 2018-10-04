package com.i2twm.order.exception;

import com.i2twm.order.enums.ResultEnum;

public class OrderException extends RuntimeException{
    private Integer code;

    public OrderException(Integer code,String message){
        super(message);
        this.code = code;
    }
    public OrderException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = code;
    }
}
