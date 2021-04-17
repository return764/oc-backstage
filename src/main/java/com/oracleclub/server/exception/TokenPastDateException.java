package com.oracleclub.server.exception;

import org.springframework.http.HttpStatus;

/**
 * @author :RETURN
 * @date :2021/4/16 0:22
 */
public class TokenPastDateException extends AbstractException {
    private boolean isRefreshToken;

    public TokenPastDateException(String message) {
        super(message);
    }

    public TokenPastDateException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    public TokenPastDateException setRefreshToken(boolean isRefreshToken){
        this.isRefreshToken = isRefreshToken;
        return this;
    }

    public boolean getIsRefreshToken(){
        return isRefreshToken;
    }
}
