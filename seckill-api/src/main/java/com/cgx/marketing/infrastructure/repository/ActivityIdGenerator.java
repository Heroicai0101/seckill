package com.cgx.marketing.infrastructure.repository;

import com.cgx.marketing.infrastructure.activity.SeckillNamespace;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ActivityIdGenerator {

    @Resource
    private RedissonClient redissonClient;

    public Long newActivityId() {
        return redissonClient.getAtomicLong(SeckillNamespace.ACTIVITY_ID).incrementAndGet();
    }

}
