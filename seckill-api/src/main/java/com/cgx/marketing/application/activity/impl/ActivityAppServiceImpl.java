package com.cgx.marketing.application.activity.impl;

import com.cgx.marketing.application.activity.ActivityAppService;
import com.cgx.marketing.domain.model.activity.*;
import com.cgx.marketing.domain.model.activity.repository.ActivityItemRepository;
import com.cgx.marketing.domain.model.activity.repository.ActivityRepository;
import com.cgx.marketing.domain.model.activity.repository.ItemSalesRepository;
import com.cgx.marketing.domain.service.ActivityService;
import com.cgx.marketing.interfaces.activity.assembler.ActivityAssembler;
import com.cgx.marketing.interfaces.activity.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityAppServiceImpl implements ActivityAppService {

    @Resource
    private ActivityRepository activityRepository;

    @Resource
    private ActivityItemRepository activityItemRepository;

    @Resource
    private ItemSalesRepository itemSalesRepository;

    @Resource
    private ActivityAssembler activityAssembler;

    @Resource
    private ActivityService activityService;

    /**
     * 配置活动及商品列表
     */
    @Override
    public Long saveActivity(SaveActivityCommand command) {
        // 活动
        Activity activity = activityAssembler.assembleActivity(command);

        // 活动商品
        ActivityId activityId = activity.getActivityId();
        List<ActivityItem> activityItems = activityAssembler.assembleActivityItem(activityId, command);

        activityService.saveActivity(activity, activityItems);
        return activityId.getId();
    }

    /**
     * 启用/禁用活动
     */
    @Override
    public void changeActivityStatus(UpdateActivityStatusCommand command) {
        ActivityId activityId = new ActivityId(command.getActivityId());
        activityService.enableActivity(activityId, command.isEnabled());
    }

    /**
     * 活动列表
     */
    @Override
    public List<ActivityDTO> activityList() {
        List<Activity> activityList = activityRepository.listActivity();
        return activityList.stream()
                           .map(act -> activityAssembler.asActivityDTO(act))
                           .collect(Collectors.toList());
    }

    /**
     * 活动详情页(透出活动商品及商品销量)
     */
    @Override
    public ActivityDetailDTO activityDetail(Long activityId) {
        // 活动信息
        ActivityId aid = new ActivityId(activityId);
        Activity act = activityRepository.findActivity(aid);
        Assert.notNull(act, "活动不存在:activityId=" + activityId);

        // 活动商品
        List<ActivityItem> activityItems = activityItemRepository.queryActivityItems(aid);

        // 商品销量
        Map<Long, Integer> itemId2Sales = itemSalesRepository.queryActivityItemSales(aid);

        List<ActivityItemDTO> itemDTOList = activityItems.stream().map(item -> {
            int itemSales = itemId2Sales.getOrDefault(item.getItemId().getId(), 0);
            return activityAssembler.assembleActivityItemDTO(item, itemSales);
        }).collect(Collectors.toList());

        return ActivityDetailDTO.builder()
                                .activityId(aid.getId())
                                .activityName(act.getActivityName())
                                .startTime(act.getStartTime())
                                .endTime(act.getEndTime())
                                .enabled(act.isEnabled())
                                .items(itemDTOList)
                                .build();
    }

    /**
     * 活动商品详情
     */
    @Override
    public ActivityItemDetailDTO activityItemDetail(ActivityId activityId, ItemId itemId) {
        // 活动商品
        Optional<ActivityItem> optItem = activityItemRepository.findActivityItem(activityId, itemId);
        Assert.isTrue(optItem.isPresent(), "商品不存在:itemId=" + itemId.getId());
        ActivityItem item = optItem.get();

        // 商品销量
        ItemSales itemSales = itemSalesRepository.queryItemSales(activityId, itemId);

        // 活动信息
        Activity act = activityRepository.findActivity(activityId);
        Assert.notNull(act, "活动不存在:activityId=" + activityId.getId());

        ActivityDTO activityDTO = ActivityDTO.builder()
                                             .activityId(act.getActivityId().getId())
                                             .activityName(act.getActivityName())
                                             .startTime(act.getStartTime())
                                             .endTime(act.getEndTime())
                                             .enabled(act.isEnabled())
                                             .build();

        return ActivityItemDetailDTO.builder()
                                    .itemId(item.getItemId().getId())
                                    .itemTitle(item.getItemTitle())
                                    .subTitle(item.getSubTitle())
                                    .itemImage(item.getItemImage())
                                    .itemPrice(item.getItemPrice())
                                    .activityPrice(item.getActivityPrice())
                                    .quota(item.getQuota())
                                    .stock(item.getStock())
                                    .sold(itemSales.getSold())
                                    .activity(activityDTO)
                                    .build();
    }

}
