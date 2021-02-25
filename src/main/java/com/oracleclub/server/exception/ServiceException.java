package com.oracleclub.server.exception;

import org.springframework.http.HttpStatus;

/**
 * @author :RETURN
 * @date :2021/2/23 21:54
 */
public class ServiceException extends AbstractException {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
