package com.cgx.marketing.infrastructure.repository;

import com.cgx.marketing.domain.model.activity.ActivityId;
import com.cgx.marketing.domain.model.activity.ItemId;
import com.cgx.marketing.domain.model.activity.ItemSales;
import com.cgx.marketing.domain.model.activity.repository.ItemSalesRepository;
import com.cgx.marketing.infrastructure.activity.SeckillNamespace;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ItemSalesRepositoryImpl implements ItemSalesRepository {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 查活动单个商品销量
     */
    @Override
    public ItemSales queryItemSales(ActivityId activityId, ItemId itemId) {
        String key = SeckillNamespace.itemSalesHash(activityId.getId());

        String sold = (String) redissonClient.getMap(key)
                                             .getOrDefault(String.valueOf(itemId.getId()), "0");
        return new ItemSales(itemId.getId(), Integer.parseInt(sold));
    }

    /**
     * 查活动全部商品销量
     */
    @Override
    public Map<Long, Integer> queryActivityItemSales(ActivityId activityId) {
        String itemSalesHash = SeckillNamespace.itemSalesHash(activityId.getId());

        RMap<String, String> itemId2Sales = redissonClient.getMap(itemSalesHash);
        return itemId2Sales.entrySet().stream()
                           .collect(Collectors.toMap(entry -> Long.parseLong(entry.getKey()),
                                   entry -> Integer.parseInt(entry.getValue())));
    }

}
