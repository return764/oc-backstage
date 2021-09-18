package com.oracleclub.server.entity.enums;

/**
 * @author : RETURN
 * @date : 2021/2/25 11:13
 */
public enum RoleEnum implements ValueEnum<Integer> {

    VISITOR(0),
    NORMAL(1),
    ADMIN(2),
    SUPER_ADMIN(3);


    private final int value;

    RoleEnum(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
