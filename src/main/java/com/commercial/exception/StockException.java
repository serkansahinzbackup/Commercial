package com.commercial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class StockException extends Exception{

    public StockException(String message){
        super(message);
    }
}
