package com.cgx.marketing.application.activity.impl;

import com.cgx.marketing.application.activity.StockAppService;
import com.cgx.marketing.domain.model.activity.*;
import com.cgx.marketing.domain.model.activity.repository.ActivityRepository;
import com.cgx.marketing.domain.model.activity.repository.StockReduceFlowRepository;
import com.cgx.marketing.domain.model.activity.rule.ActivityAccessContext;
import com.cgx.marketing.domain.model.activity.rule.ActivityRuleCheckResult;
import com.cgx.marketing.domain.service.StockReduceService;
import com.cgx.marketing.domain.shard.BizStatusCode;
import com.cgx.marketing.interfaces.activity.assembler.StockReduceFlowAssembler;
import com.cgx.marketing.interfaces.activity.dto.CancelReduceCommand;
import com.cgx.marketing.interfaces.activity.dto.ReduceCommand;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * 库存扣减应用服务
 */
@Service
public class StockAppServiceImpl implements StockAppService {

    @Resource
    private ActivityRepository activityRepository;

    @Resource
    private StockReduceFlowRepository stockReduceFlowRepository;

    @Resource
    private StockReduceFlowAssembler stockReduceFlowAssembler;

    @Resource
    private StockReduceService stockReduceService;

    /**
     * 扣库存
     */
    @Override
    public StockReduceResult reduce(@NonNull ReduceCommand command) {
        ActivityId activityId = new ActivityId(command.getActivityId());
        StockReduceFlow reduceFlow = stockReduceFlowAssembler.assembleStockReduceFlow(command);

        // 前置校验: 活动是否存在
        Activity activity = activityRepository.findActivity(activityId);
        if (Objects.isNull(activity)) {
            return StockReduceResult.error(BizStatusCode.ACTIVITY_NOT_EXISTS, activityId.getId());
        }

        // 前置校验: 活动是否进行中
        OrderInfo orderInfo = reduceFlow.getOrderInfo();
        if (!activity.onSale(orderInfo.getOrderTime())) {
            return StockReduceResult.error(BizStatusCode.ACTIVITY_OFFLINE, activityId.getId());
        }

        // 活动准入规则校验
        ActivityAccessContext accessContext = stockReduceFlowAssembler.assembleActivityAccessContext(command);
        ActivityRuleCheckResult activityRuleCheckResult = activity.canPass(accessContext);
        if (!activityRuleCheckResult.isPass()) {
            return StockReduceResult.error(activityRuleCheckResult.getErrmsg());
        }

        // 扣库存
        return stockReduceService.reduce(reduceFlow);
    }

    /**
     * 回库存
     */
    @Override
    public StockReduceResult cancelReduce(@NonNull CancelReduceCommand command) {
        ActivityId activityId = new ActivityId(command.getActivityId());
        OrderId orderId = new OrderId(command.getOrderId());

        Optional<StockReduceFlow> optFlow = stockReduceFlowRepository.queryStockReduceFlow(activityId, orderId);
        if (optFlow.isPresent()) {
            StockReduceFlow reduceFlow = optFlow.get();
            return stockReduceService.cancelReduce(reduceFlow);
        }
        return StockReduceResult.ok();
    }

}
