package com.oracleclub.server.converter;

import com.oracleclub.server.utils.BeanUtils;
import com.oracleclub.server.utils.ReflectionUtils;
import org.springframework.lang.Nullable;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * @author :RETURN
 * @date :2021/2/22 16:36
 */
public interface InputConverter<DOMAIN> {

    default DOMAIN convertTo() {
        // Get parameterized type
        ParameterizedType currentType = parameterizedType();

        // Assert not equal
        Objects.requireNonNull(currentType, "Cannot fetch actual type because parameterized type is null");

        Class<DOMAIN> domainClass = (Class<DOMAIN>) currentType.getActualTypeArguments()[0];

        return BeanUtils.transformFrom(this, domainClass);
    }

    default void update(DOMAIN domain) {
        BeanUtils.updateProperties(this, domain);
    }


    @Nullable
    default ParameterizedType parameterizedType() {
        return ReflectionUtils.getParameterizedType(InputConverter.class, this.getClass());
    }
}
