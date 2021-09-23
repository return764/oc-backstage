package com.oracleclub.server.entity.support;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;

/**
 * @author :RETURN
 * @date :2021/2/26 0:23
 */
@Component
public class CustomIdGenerator implements IdentifierGenerator {

    private final Snowflake snowflake = IdUtil.getSnowflake(1,1);

    @Override
    public Number nextId(Object entity) {
        return snowflake.nextId();
    }

    @Override
    public String nextUUID(Object entity) {
        return snowflake.nextIdStr();
    }
}
