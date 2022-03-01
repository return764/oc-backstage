package com.oracleclub.server.annotation;


import com.oracleclub.server.entity.enums.OperationType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLogMarker {

    OperationType operaType() default OperationType.UNKNOWN;

    String operaContent() default "";
}
