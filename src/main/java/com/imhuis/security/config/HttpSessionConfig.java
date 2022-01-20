package com.imhuis.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
@Configuration
@EnableRedisHttpSession(redisNamespace = "ss:session", cleanupCron = "0 0 * * * *",
        maxInactiveIntervalInSeconds = 3600)
public class HttpSessionConfig implements BeanClassLoaderAware {

    private ClassLoader classLoader;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(objectMapper());
    }

    private ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(SecurityJackson2Modules.getModules(this.classLoader));
        return objectMapper;
    }

//    @Bean
//    public HttpSessionIdResolver httpSessionIdResolver() {
//        return HeaderHttpSessionIdResolver.xAuthToken();
//    }
//
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher(){
//        return new HttpSessionEventPublisher();
//    }

}
