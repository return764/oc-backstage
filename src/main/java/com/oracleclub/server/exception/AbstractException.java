package com.oracleclub.server.exception;

import org.springframework.http.HttpStatus;

/**
 * @author :RETURN
 * @date :2021/2/23 21:05
 */
public abstract class AbstractException extends RuntimeException {

    private Object errorData;

    public AbstractException(String message){
        super(message);
    }

    public AbstractException(String message,Throwable cause){
        super(message,cause);
    }

    public abstract HttpStatus getStatus();

    public Object getErrorData(){
        return errorData;
    }

    public AbstractException setErrorData(Object errorData){
        this.errorData = errorData;
        return this;
    }
}
