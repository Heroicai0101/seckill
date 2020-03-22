package com.cgx.marketing.interfaces.activity.web;

import com.cgx.marketing.application.activity.StockAppService;
import com.cgx.marketing.domain.model.activity.StockReduceResult;
import com.cgx.marketing.interfaces.activity.dto.CancelReduceCommand;
import com.cgx.marketing.module.response.Response;
import com.cgx.marketing.module.response.ResponseBuilder;
import com.cgx.marketing.interfaces.activity.dto.ReduceCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "库存扣减接口")
@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

    @Resource
    private StockAppService stockAppService;

    @ApiOperation(value = "减库存")
    @PostMapping("/reduce")
    public Response<StockReduceResult> tryReduce(@RequestBody ReduceCommand command) {
        StockReduceResult res = stockAppService.reduce(command);
        return ResponseBuilder.ok(res);
    }

    @ApiOperation(value = "回库存")
    @PostMapping("/cancelReduce")
    public Response<StockReduceResult> cancelReduce(@RequestBody CancelReduceCommand command) {
        StockReduceResult res = stockAppService.cancelReduce(command);
        return ResponseBuilder.ok(res);
    }

}
