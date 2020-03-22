package com.cgx.marketing.infrastructure.repository;

import com.cgx.marketing.domain.model.activity.ActivityId;
import com.cgx.marketing.domain.model.activity.ActivityItem;
import com.cgx.marketing.domain.model.activity.ItemId;
import com.cgx.marketing.domain.model.activity.repository.ActivityItemRepository;
import com.cgx.marketing.infrastructure.activity.SeckillNamespace;
import com.cgx.marketing.infrastructure.model.ActivityItemDO;
import com.cgx.marketing.infrastructure.repository.convert.ActivityItemConverter;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ActivityItemRepositoryImpl implements ActivityItemRepository {

    @Resource
    private Gson gson;

    @Resource
    private RedissonClient redissonClient;

    /**
     * 查指定活动商品配置
     */
    @Override
    public Optional<ActivityItem> findActivityItem(ActivityId activityId, ItemId itemId) {
        String key = SeckillNamespace.activityItemsHash(activityId.getId());

        String item = (String) redissonClient.getMap(key).getOrDefault(String.valueOf(itemId.getId()), "");
        if (StringUtils.isEmpty(item)) {
            return Optional.empty();
        }
        ActivityItemDO itemDO = gson.fromJson(item, ActivityItemDO.class);
        ActivityItem activityItem = ActivityItemConverter.fromItemDO(itemDO);
        return Optional.of(activityItem);
    }

    @Override
    public void saveActivityItem(ActivityId activityId, List<ActivityItem> activityItems) {
        // 先删除活动下配置的商品列表再设置, 保证幂等
        String activityItemsHash = SeckillNamespace.activityItemsHash(activityId.getId());
        redissonClient.getMap(activityItemsHash).delete();

        Map<String, String> itemId2ItemMap = Maps.newHashMap();
        activityItems.forEach(item -> {
            ActivityItemDO itemDO = ActivityItemConverter.toItemDO(item);
            itemId2ItemMap.put(String.valueOf(item.getItemId().getId()), gson.toJson(itemDO));
        });
        redissonClient.getMap(activityItemsHash).putAll(itemId2ItemMap);
    }

    /**
     * 查活动商品列表(缺商品销量)
     */
    @Override
    public List<ActivityItem> queryActivityItems(ActivityId activityId) {
        String activityItemsHash = SeckillNamespace.activityItemsHash(activityId.getId());

        Map<String, String> itemMap = redissonClient.getMap(activityItemsHash);
        return itemMap.values().stream().map(item -> {
            ActivityItemDO itemDO = gson.fromJson(item, ActivityItemDO.class);
            return ActivityItemConverter.fromItemDO(itemDO);
        }).collect(Collectors.toList());
    }

}
