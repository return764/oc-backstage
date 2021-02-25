package com.oracleclub.server.exception;

import org.springframework.http.HttpStatus;

/**
 * @author :RETURN
 * @date :2021/2/23 21:10
 */
public class NotFoundException extends AbstractException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
