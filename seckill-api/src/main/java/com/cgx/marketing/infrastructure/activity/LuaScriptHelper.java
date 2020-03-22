package com.cgx.marketing.infrastructure.activity;

import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class LuaScriptHelper {

    private final RedissonClient redissonClient;

    private LuaScriptHelper(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public String evalLuaScript(List<Object> keys, Object[] values, String script) {
        return redissonClient.getScript()
                             .eval(RScript.Mode.READ_WRITE, StringCodec.INSTANCE, script, RScript.ReturnType.VALUE,
                                     keys, values);
    }

    public static LuaScriptHelper create(RedissonClient redissonClient) {
        return new LuaScriptHelper(redissonClient);
    }

    /**
     * 读取Lua脚本
     */
    public static String readScript(String resourcePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(resourcePath);
        return new String(FileCopyUtils.copyToByteArray(resource.getInputStream()), Charset.forName("UTF-8"));
    }

}
