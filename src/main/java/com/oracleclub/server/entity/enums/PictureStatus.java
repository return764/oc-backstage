package com.oracleclub.server.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author :RETURN
 * @date :2021/2/25 13:18
 */
public enum PictureStatus implements ValueEnum<Integer> {
    EXIST(0),
    DELETED(1);

    @EnumValue
    private final int value;

    PictureStatus(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
