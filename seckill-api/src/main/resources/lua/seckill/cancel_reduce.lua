--[[
 KEYS[1] 库存扣减流水, KEYS[2] 买家已购, KEYS[3] 商品销量
 ARGV[1] 订单id
--]]
local STOCK_REDUCE_FLOW_HASH = KEYS[1];
local BUYER_HOLD_HASH = KEYS[2];
local ITEM_SALES_HASH = KEYS[3];
local orderId = ARGV[1];

-- 幂等控制
local flowExists = redis.call("HEXISTS", STOCK_REDUCE_FLOW_HASH, orderId);
if flowExists == 0 then
    return "NO_REDUCE_FLOW";
end

local flow = redis.call("HGET", STOCK_REDUCE_FLOW_HASH, orderId);
local payload = cjson.decode(flow);

-- 减商品销量、减买家已购
redis.call("HINCRBY", BUYER_HOLD_HASH, payload["buyerId"], -1 * tonumber(payload["quantity"]));
redis.call("HINCRBY", ITEM_SALES_HASH, payload["itemId"], -1 * tonumber(payload["quantity"]));

-- 删除库存扣减流水
redis.call("HDEL", STOCK_REDUCE_FLOW_HASH, orderId);
return "OK";
