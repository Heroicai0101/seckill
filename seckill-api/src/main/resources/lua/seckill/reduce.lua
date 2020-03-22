--[[
 KEYS[1] 库存扣减流水, KEYS[2] 活动商品, KEYS[3] 买家已购, KEYS[4] 商品销量
 ARGV[1] 订单id, ARGV[2] 买家id, ARGV[3] 商品id, ARGV[4] 抢购数量, ARGV[5] json化库存扣减流水
--]]
local orderId = ARGV[1];
local buyerId = ARGV[2];
local itemId = ARGV[3];

-- 防重判断
local STOCK_REDUCE_FLOW_HASH = KEYS[1];
local flowExists = redis.call("HEXISTS", STOCK_REDUCE_FLOW_HASH, orderId);
if flowExists == 1 then
    return "REPEATED_REQUEST";
end

-- 校验商品是否参加了活动
local ACTIVITY_ITEMS_HASH = KEYS[2];
local activityExists = redis.call("HEXISTS", ACTIVITY_ITEMS_HASH, itemId);
if activityExists == 0 then
    return "ITEM_ACTIVITY_ABSENT";
end

-- 加载活动商品配置
local config = redis.call("HGET", ACTIVITY_ITEMS_HASH, itemId);
local payload = cjson.decode(config);

-- 用户已购数量
local BUYER_HOLD_HASH = KEYS[3];
local bookedCount = redis.call("HGET", BUYER_HOLD_HASH, buyerId);
if bookedCount == false then
    bookedCount = 0;
end
if bookedCount + tonumber(ARGV[4]) > tonumber(payload["quota"]) then
    return "QUOTA_NOT_ENOUGH";
end

-- 商品累计售出数量
local ITEM_SALES_HASH = KEYS[4];
local soldCount = redis.call("HGET", ITEM_SALES_HASH, itemId);
if soldCount == false then
    soldCount = 0;
end
if soldCount + tonumber(ARGV[4]) > tonumber(payload["stock"]) then
    return "STOCK_NOT_ENOUGH";
end

-- 记录库存扣减流水、增买家已购、增商品销量
redis.call("HSET", STOCK_REDUCE_FLOW_HASH, orderId, ARGV[5]);
redis.call("HINCRBY", BUYER_HOLD_HASH, buyerId, ARGV[4]);
redis.call("HINCRBY", ITEM_SALES_HASH, itemId, ARGV[4]);
return "OK";
