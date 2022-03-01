package com.oracleclub.server.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author :RETURN
 * @date :2021/2/25 11:20
 */
public enum UserStatus {
    NOT_ACTIVE(0),
    ACTIVE(1),
    SUSPEND(2);

    @EnumValue
    private final int value;

    UserStatus(int value) {
        this.value = value;
    }
}
