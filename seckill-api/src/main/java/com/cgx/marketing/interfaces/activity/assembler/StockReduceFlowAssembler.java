package com.cgx.marketing.interfaces.activity.assembler;

import com.cgx.marketing.domain.model.activity.*;
import com.cgx.marketing.domain.model.activity.rule.ActivityAccessContext;
import com.cgx.marketing.interfaces.activity.dto.ActivityAccessContextDTO;
import com.cgx.marketing.interfaces.activity.dto.ReduceCommand;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StockReduceFlowAssembler {

    /**
     * 从command组装库存扣减流水
     */
    public StockReduceFlow assembleStockReduceFlow(@NonNull ReduceCommand command) {
        OrderInfo order = OrderInfo.builder()
                                   .orderId(new OrderId(command.getOrderId()))
                                   .orderTime(command.getOrderTime())
                                   .itemId(new ItemId(command.getItemId()))
                                   .quantity(command.getQuantity())
                                   .build();

        ActivityId activityId = new ActivityId(command.getActivityId());
        return StockReduceFlow.builder()
                              .activityId(activityId)
                              .buyerId(new BuyerId(command.getBuyerId()))
                              .orderInfo(order)
                              .build();
    }


    /**
     * 从command组装库存扣减流水
     */
    public ActivityAccessContext assembleActivityAccessContext(@NonNull ReduceCommand command) {
        ActivityAccessContextDTO contextDTO = command.getActivityAccessContext();
        if (Objects.isNull(contextDTO)) {
            return null;
        }

        return ActivityAccessContext.builder()
                                    .cityId(contextDTO.getCityId())
                                    .storeId(contextDTO.getStoreId())
                                    .build();
    }

}
