package com.oracleclub.server.entity.enums;

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

    private final Integer value;

    UploadFileType(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
