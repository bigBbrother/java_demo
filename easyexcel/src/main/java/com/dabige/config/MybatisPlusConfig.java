package com.dabige.config;

import com.dabige.mapper.SpiceSqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MybatisPlusConfig {
    /**
     * 定义批量插入方法
     */
    @Bean
    @Primary
    public SpiceSqlInjector customSqlInterceptor() {
        return new SpiceSqlInjector();
    }

}
