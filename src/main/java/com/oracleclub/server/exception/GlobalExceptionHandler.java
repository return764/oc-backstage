package com.oracleclub.server.exception;

import com.oracleclub.server.entity.vo.R;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.naming.AuthenticationException;

/**
 * 全局异常捕获
 * @author RETURN
 * @date 2020/8/14 0:21
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R runtimeExceptionHandle(Exception e){
        e.printStackTrace();
        return R.failed(e.getMessage());
    }

    @ExceptionHandler(EmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R emailExceptionHandle(Exception e){
        return R.failed(e.getMessage());
    }

    @ExceptionHandler(PictureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R pictureExceptionHandle(Exception e){
        return R.failed(e.getMessage());
    }

    @ExceptionHandler(UploadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R uploadExceptionHandle(Exception e){
        return R.failed(e.getMessage());
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R userExceptionHandle(Exception e){
        return R.failed(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R authExceptionHandle(AuthenticationException e){
        return R.failed(e.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R uploadSizeExceptionHandle(MaxUploadSizeExceededException e){
        return R.failed("上传文件大小超过限制");
    }

    @ExceptionHandler(LoginException.class)
    public R loginExceptionHandle(LoginException e){
        return R.failed(e.getMessage());
    }
}
