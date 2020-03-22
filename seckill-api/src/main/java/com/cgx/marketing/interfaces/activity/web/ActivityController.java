package com.cgx.marketing.interfaces.activity.web;

import com.cgx.marketing.application.activity.ActivityAppService;
import com.cgx.marketing.domain.model.activity.ActivityId;
import com.cgx.marketing.domain.model.activity.ItemId;
import com.cgx.marketing.interfaces.activity.dto.*;
import com.cgx.marketing.module.response.Response;
import com.cgx.marketing.module.response.ResponseBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "活动接口")
@RestController
@RequestMapping("/api/v1/activity")
public class ActivityController {

    @Resource
    private ActivityAppService activityAppService;

    @ApiOperation(value = "配置活动")
    @PostMapping("/save")
    public Response<Long> saveActivity(@RequestBody SaveActivityCommand command) {
        Long activityId = activityAppService.saveActivity(command);
        return ResponseBuilder.ok(activityId);
    }

    @ApiOperation(value = "启用/禁用活动")
    @PostMapping("/changeStatus")
    public Response<Void> changeActivityStatus(@RequestBody UpdateActivityStatusCommand command) {
        activityAppService.changeActivityStatus(command);
        return ResponseBuilder.ok();
    }

    @ApiOperation(value = "活动列表")
    @GetMapping("/list")
    public Response<List<ActivityDTO>> activityList() {
        List<ActivityDTO> dto = activityAppService.activityList();
        return ResponseBuilder.ok(dto);
    }

    @ApiOperation(value = "活动详情(透出活动商品及商品销量)")
    @GetMapping("/detail")
    public Response<ActivityDetailDTO> activityDetail(
            @ApiParam(value = "activityId", defaultValue = "1") @RequestParam("activityId") Long activityId) {
        ActivityDetailDTO activityDetailDTO = activityAppService.activityDetail(activityId);
        return ResponseBuilder.ok(activityDetailDTO);
    }

    @ApiOperation(value = "活动商品详情(透出商品销量)")
    @GetMapping("/itemDetail")
    public Response<ActivityItemDetailDTO> activityItemDetail(
            @ApiParam(value = "activityId", defaultValue = "1") @RequestParam("activityId") Long activityId,
            @ApiParam(value = "itemId", defaultValue = "53724") @RequestParam("itemId") Long itemId) {
        ActivityId curActivityId = new ActivityId(activityId);
        ItemId curItemId = new ItemId(itemId);

        ActivityItemDetailDTO activityDetailDTO = activityAppService.activityItemDetail(curActivityId, curItemId);
        return ResponseBuilder.ok(activityDetailDTO);
    }

}
