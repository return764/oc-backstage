package com.oracleclub.server.entity.enums;

import org.springframework.util.Assert;

import java.util.stream.Stream;

/**
 * @author :RETURN
 * @date :2021/2/23 16:46
 */
public interface ValueEnum<T> {

    static <V, E extends ValueEnum<V>> E valueToEnum(Class<E> enumType, V value) {
        Assert.notNull(enumType, "enum type must not be null");
        Assert.notNull(value, "value must not be null");
        Assert.isTrue(enumType.isEnum(), "type must be an enum type");

        return Stream.of(enumType.getEnumConstants())
                .filter(item -> item.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown database value: " + value));
    }

    T getValue();
}
