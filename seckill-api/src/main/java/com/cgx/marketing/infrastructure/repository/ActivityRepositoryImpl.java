package com.cgx.marketing.infrastructure.repository;

import com.cgx.marketing.domain.model.activity.Activity;
import com.cgx.marketing.domain.model.activity.ActivityId;
import com.cgx.marketing.domain.model.activity.repository.ActivityRepository;
import com.cgx.marketing.infrastructure.activity.SeckillNamespace;
import com.cgx.marketing.infrastructure.model.ActivityDO;
import com.cgx.marketing.infrastructure.repository.convert.ActivityConverter;
import com.google.gson.Gson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ActivityRepositoryImpl implements ActivityRepository {

    @Resource
    private Gson gson;

    @Resource
    private RedissonClient redissonClient;

    /**
     * 活动列表
     */
    @Override
    public List<Activity> listActivity() {
        String activityCatalogHash = SeckillNamespace.activityCatalogHash();

        RMap<String, String> activityMap = redissonClient.getMap(activityCatalogHash);
        List<ActivityDO> activityList = activityMap.values().stream()
                                                   .map(activity -> gson.fromJson(activity, ActivityDO.class))
                                                   .collect(Collectors.toList());
        return activityList.stream()
                           .map(ActivityConverter::fromDO)
                           .collect(Collectors.toList());
    }

    /**
     * 根据活动id查活动
     */
    @Override
    public Activity findActivity(ActivityId activityId) {
        String activityCatalogHash = SeckillNamespace.activityCatalogHash();

        Map<String, String> activityMap = redissonClient.getMap(activityCatalogHash);
        String activity = activityMap.get(String.valueOf(activityId.getId()));
        Assert.hasText(activity, "活动不存在:activityId=" + activityId.getId());

        ActivityDO activityDO = gson.fromJson(activity, ActivityDO.class);
        return ActivityConverter.fromDO(activityDO);
    }

    /**
     * 保存活动
     */
    @Override
    public void saveActivity(Activity activity) {
        ActivityId activityId = activity.getActivityId();
        ActivityDO activityDO = ActivityConverter.toDO(activity);

        String activityCatalogHash = SeckillNamespace.activityCatalogHash();
        redissonClient.getMap(activityCatalogHash)
                      .put(String.valueOf(activityId.getId()), gson.toJson(activityDO));
    }

}
