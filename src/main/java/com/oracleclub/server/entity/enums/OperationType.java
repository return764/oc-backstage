package com.oracleclub.server.entity.enums;

public enum OperationType implements ValueEnum<String> {
    NEW("新增"),
    UNKNOWN("未知"),
    UPDATE("更新"),
    DELETE("删除"),
    LOGOUT("登出"),
    UPLOAD("上传");

    private final String type;

    OperationType(String type) {
        this.type = type;
    }

    @Override
    public String getValue() {
        return type;
    }
}
