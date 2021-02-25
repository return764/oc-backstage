package com.oracleclub.server.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author :RETURN
 * @date :2021/2/22 17:22
 */
public class ReflectionUtils {

    @Nullable
    public static ParameterizedType getParameterizedType(@NonNull Class<?> superType, Type... genericTypes) {
        Assert.notNull(superType, "Interface or super type must not be null");

        ParameterizedType currentType = null;

        for (Type genericType : genericTypes) {
            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                if (parameterizedType.getRawType().getTypeName().equals(superType.getTypeName())) {
                    currentType = parameterizedType;
                    break;
                }
            }
        }

        return currentType;
    }
}
