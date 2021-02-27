package com.oracleclub.server.exception;

/**
 * @author :RETURN
 * @date :2021/2/27 17:07
 */
public class FileOperationException extends ServiceException {
    public FileOperationException(String message) {
        super(message);
    }

    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }

}
