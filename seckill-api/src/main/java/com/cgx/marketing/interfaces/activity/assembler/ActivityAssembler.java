package com.cgx.marketing.interfaces.activity.assembler;

import com.cgx.marketing.domain.model.activity.Activity;
import com.cgx.marketing.domain.model.activity.ActivityId;
import com.cgx.marketing.domain.model.activity.ActivityItem;
import com.cgx.marketing.domain.model.activity.ItemId;
import com.cgx.marketing.domain.model.activity.rule.ActivityRule;
import com.cgx.marketing.domain.model.activity.rule.ActivityRuleConfig;
import com.cgx.marketing.domain.model.activity.rule.ActivityRuleRegistrar;
import com.cgx.marketing.infrastructure.repository.ActivityIdGenerator;
import com.cgx.marketing.interfaces.activity.dto.ActivityDTO;
import com.cgx.marketing.interfaces.activity.dto.ActivityItemDTO;
import com.cgx.marketing.interfaces.activity.dto.ActivityRuleConfigDTO;
import com.cgx.marketing.interfaces.activity.dto.SaveActivityCommand;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class ActivityAssembler {

    @Resource
    private ActivityIdGenerator activityIdGenerator;

    /**
     * 从command组装活动信息
     */
    public Activity assembleActivity(@NonNull SaveActivityCommand command) {
        Activity activity = new Activity();

        boolean enabled = command.isEnabled();
        Long aid = command.getActivityId();
        if (aid == null) {
            // 没有则生成活动id
            aid = activityIdGenerator.newActivityId();
            // 新活动一开始都是禁用状态
            enabled = false;
        }

        ActivityId activityId = new ActivityId(aid);
        activity.setActivityId(activityId);
        activity.setActivityName(command.getActivityName());
        activity.setStartTime(command.getStartTime());
        activity.setEndTime(command.getEndTime());
        activity.setEnabled(enabled);

        // 活动规则
        List<ActivityRuleConfigDTO> ruleConfigDTOs = command.getActivityRuleConfigs();
        List<ActivityRule> activityRules = assembleActivityRules(ruleConfigDTOs, activityId.getId());
        activity.setActivityRules(activityRules);
        return activity;
    }

    public List<ActivityRule> assembleActivityRules(List<ActivityRuleConfigDTO> configDTOs, Long activityId) {
        if (CollectionUtils.isEmpty(configDTOs)) {
            return Lists.newArrayList();
        }

        return configDTOs.stream().map(dto -> {
            String configKey = dto.getConfigKey();
            ActivityRuleConfig ruleConfig = new ActivityRuleConfig();
            ruleConfig.setActivityId(activityId);
            ruleConfig.setConfigKey(configKey);
            ruleConfig.setConfigValue(dto.getConfigValue());
            return ActivityRuleRegistrar.parseRule(ruleConfig);
        }).collect(Collectors.toList());
    }

    /**
     * 从command组装活动商品
     */
    public List<ActivityItem> assembleActivityItem(@NonNull ActivityId activityId,
                                                   @NonNull SaveActivityCommand command) {
        List<ActivityItemDTO> itemDTOs = command.getItemLine();
        return itemDTOs.stream().map(itemDto -> {
            ActivityItem item = new ActivityItem();
            item.setActivityId(activityId);

            item.setItemId(new ItemId(itemDto.getItemId()));
            item.setItemTitle(itemDto.getItemTitle());
            item.setSubTitle(itemDto.getSubTitle());
            item.setItemImage(itemDto.getItemImage());
            item.setItemPrice(itemDto.getItemPrice());
            item.setActivityPrice(itemDto.getActivityPrice());
            item.setQuota(itemDto.getQuota());
            item.setStock(itemDto.getStock());
            return item;
        }).collect(toList());
    }

    public ActivityItemDTO assembleActivityItemDTO(ActivityItem item, Integer itemSales) {
        ActivityItemDTO dto = new ActivityItemDTO();
        dto.setItemId(item.getItemId().getId());
        dto.setItemTitle(item.getItemTitle());
        dto.setSubTitle(item.getSubTitle());
        dto.setItemImage(item.getItemImage());
        dto.setItemPrice(item.getItemPrice());
        dto.setActivityPrice(item.getActivityPrice());
        dto.setQuota(item.getQuota());
        dto.setStock(item.getStock());

        // 无销量，填充0
        int sold = Objects.nonNull(itemSales) ? Math.min(itemSales, item.getStock()) : 0;
        dto.setSold(sold);
        return dto;
    }

    public ActivityDTO asActivityDTO(Activity activity) {
        return ActivityDTO.builder()
                          .activityId(activity.getActivityId().getId())
                          .activityName(activity.getActivityName())
                          .startTime(activity.getStartTime())
                          .endTime(activity.getEndTime())
                          .enabled(activity.isEnabled())
                          .build();
    }

}
