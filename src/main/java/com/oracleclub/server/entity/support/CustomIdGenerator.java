package com.oracleclub.server.entity.support;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

/**
 * @author :RETURN
 * @date :2021/2/26 0:23
 */
public class CustomIdGenerator extends IdentityGenerator {

    private final Snowflake snowflake = IdUtil.getSnowflake(1,1);

    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        return snowflake.nextId();
    }
}
