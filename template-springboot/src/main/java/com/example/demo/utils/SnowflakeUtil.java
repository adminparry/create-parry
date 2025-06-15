package com.example.demo.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class SnowflakeUtil implements IdentifierGenerator {

    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return snowflake.nextId();
    }
}