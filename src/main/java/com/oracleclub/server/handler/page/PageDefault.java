package com.oracleclub.server.handler.page;

import java.lang.annotation.*;

/**
 * @author :RETURN
 * @date :2021/9/22 9:13
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface PageDefault {
    int value() default 10;

    int size() default 10;

    int page() default 1;
}

