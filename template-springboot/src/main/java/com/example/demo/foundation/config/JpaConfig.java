package com.example.demo.foundation.config;

import cn.hutool.core.lang.Snowflake;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
public class JpaConfig {
    @Bean
    public Snowflake snowflake(){
        return new Snowflake(1,1);
    }


    @Bean
    public IdentifierGenerator identifierGenerator(Snowflake snowflake){
        return new IdentifierGenerator() {
            @Override
            public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
                return snowflake.nextId();
            }
        };
    }
}
