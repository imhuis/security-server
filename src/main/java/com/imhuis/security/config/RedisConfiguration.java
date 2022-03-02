package com.imhuis.security.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
@Configuration
public class RedisConfiguration {

    @Bean(destroyMethod = "destroy")
    public LettuceConnectionFactory lettuceConnectionFactory(){
//        RedisClusterConfiguration redisClusterConnection = new RedisClusterConfiguration();
//        redisClusterConnection.addClusterNode(new RedisNode("redis1",26379));
//        redisClusterConnection.addClusterNode(new RedisNode("redis2",26379));
//        redisClusterConnection.addClusterNode(new RedisNode("redis3",26379));
//        redisClusterConnection.setMaxRedirects(3);
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();

        ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                .enablePeriodicRefresh(Duration.ofSeconds(60))
                .enableAdaptiveRefreshTrigger(ClusterTopologyRefreshOptions.RefreshTrigger.ASK_REDIRECT, ClusterTopologyRefreshOptions.RefreshTrigger.UNKNOWN_NODE)
                .build();

        ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
                .topologyRefreshOptions(clusterTopologyRefreshOptions)// 拓扑刷新
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .autoReconnect(true)
                .socketOptions(SocketOptions.builder().keepAlive(true).build())
                .validateClusterNodeMembership(false)// 取消校验集群节点的成员关系
                .build();

        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
//                .clientOptions(clusterClientOptions)
//                .readFrom(ReadFrom.ANY)
                .commandTimeout(Duration.ofMillis(1000))
                .poolConfig(genericObjectPoolConfig())
                .build();

        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfiguration);
    }


    public GenericObjectPoolConfig genericObjectPoolConfig(){
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMinIdle(5);
        genericObjectPoolConfig.setMaxIdle(10);
        genericObjectPoolConfig.setMaxTotal(20);
        return genericObjectPoolConfig;
    }

    @Bean
    public RedisTemplate redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        return redisTemplate;
    }

}