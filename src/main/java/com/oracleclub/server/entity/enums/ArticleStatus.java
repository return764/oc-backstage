package com.oracleclub.server.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author :RETURN
 * @date :2021/2/23 16:37
 */
public enum ArticleStatus implements ValueEnum<Integer> {

    UNPUBLISHED(0),
    PUBLISHED(1),
    DELETED(2);


    @EnumValue
    private final int value;

    ArticleStatus(int value){
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
