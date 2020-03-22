package com.cgx.marketing.infrastructure.common.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "marketing.redis")
public class RedisClientConfig {

    /** 节点地址，redis://host:port的格式 */
    private String address;

    /** 连接超时，单位：毫秒 */
    private int connectTimeout;

    /** 响应超时，单位：毫秒 */
    private int timeout;

    /** 最小空闲连接数 */
    private int minIdlePoolSize;

    /** 连接池最大容量 */
    private int maxPoolSize;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnClass(Config.class)
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setCodec(StringCodec.INSTANCE);
        config.useSingleServer()
              .setAddress(address)
              .setConnectTimeout(connectTimeout)
              .setTimeout(timeout)
              .setConnectionMinimumIdleSize(minIdlePoolSize)
              .setConnectionPoolSize(maxPoolSize);
        return Redisson.create(config);
    }
}
