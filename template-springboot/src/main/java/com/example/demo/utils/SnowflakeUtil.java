package com.example.demo.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import java.io.Serializable;

public class SnowflakeUtil {

    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    public Serializable generate() {
        return snowflake.nextId();
    }
}