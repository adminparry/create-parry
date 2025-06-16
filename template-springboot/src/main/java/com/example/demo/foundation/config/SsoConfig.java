package com.example.demo.foundation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "sso")
public class SsoConfig {

    private List<String> whiteList;
    // JWT配置参数
    private  String SECRET_KEY; // 实际项目应从配置读取
    private  long JWT_EXPIRATION; // 24小时
    private  String REDIS_PREFIX; // Redis键前缀
    private  boolean enable;



    public boolean isInWhiteList(HttpServletRequest request){

        if(null !=  whiteList){
            boolean inWhiteList = whiteList.stream().anyMatch((item) -> (new AntPathMatcher()).match(item, request.getServletPath()));
            if(inWhiteList){
                return true;
            }
        }
        return false;
    }


//    @Bean
//    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
//        StringRedisTemplate template = new StringRedisTemplate(factory);
//        template.setEnableTransactionSupport(true);
//        return template;
//    }
}