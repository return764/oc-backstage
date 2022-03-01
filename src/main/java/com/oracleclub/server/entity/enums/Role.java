package com.oracleclub.server.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author : RETURN
 * @date : 2021/2/25 11:13
 */
public enum Role implements ValueEnum<Integer> {

    VISITOR(0),
    NORMAL(1),
    ADMIN(2),
    SUPER_ADMIN(3);

    @EnumValue
    private final int value;

    Role(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
