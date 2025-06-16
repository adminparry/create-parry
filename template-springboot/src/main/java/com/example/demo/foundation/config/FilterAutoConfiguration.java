package com.example.demo.foundation.config;

import com.example.demo.foundation.filter.JwtAuthenticationFilter;
import com.example.demo.utils.SsoUtil;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Author: uf104259
 * @Date: 6/12/2025
 * @Description:
 */
@Configuration
@Conditional({SsoAppCondition.class})
@EnableConfigurationProperties({JwtProps.class, SsoConfig.class})
public class FilterAutoConfiguration {

    private static final int JWT_ORDER = -1;


    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilterFilterRegistrationBean(SsoConfig ssoConfig, SsoUtil ssoUtil){
        FilterRegistrationBean<JwtAuthenticationFilter> bean = new FilterRegistrationBean<>(new JwtAuthenticationFilter(ssoUtil,ssoConfig),new ServletRegistrationBean[0]);
        bean.setOrder(JWT_ORDER);
        return bean;
    }



}
//有一个条件满足即可
class JwtCondition extends AnyNestedCondition {
    public JwtCondition(ConfigurationPhase configurationPhase) {
        super(configurationPhase);
    }

    @ConditionalOnProperty(
            prefix = "app.jwt",
            name = {"enable"}
    )
    static class Enable{
        public Enable() {
        }
    }
    @Conditional({SsoAppCondition.class})
    static class Enable1 {
        public Enable1() {
        }
    }
}


class SsoAppCondition implements Condition {
    public SsoAppCondition() {
    }

    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env = context.getEnvironment();
        Boolean sso = (Boolean)env.getProperty("sso.enable", Boolean.class);
        return sso;
    }
}


