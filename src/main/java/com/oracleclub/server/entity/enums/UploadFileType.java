package com.oracleclub.server.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author :RETURN
 * @date :2021/2/27 21:22
 */
public enum UploadFileType implements ValueEnum<Integer> {
    /**
     * 本地
     */
    LOCAL(0),
    /**
     * 阿里云
     */
    OSS(1);

    @EnumValue
    private final Integer value;

    UploadFileType(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
