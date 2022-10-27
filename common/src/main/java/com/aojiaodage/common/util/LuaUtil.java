package com.aojiaodage.common.util;

public class LuaUtil {
    // 查询并删除
    public static final String luaScript1 = "for i,k in pairs(KEYS) do local v=redis.call('get', k); if v then redis.call('del', k); end; end; return 1;";
    // 查询多个key
    public static final String luaScript2 = "local r={}; for i,k in pairs(KEYS) do r[i]=redis.call('get', k) end; return r;";
    // 设置多个值【这里的脚本放在redis执行是成功的，但是调用redisTemplate.execute()时失败，它这个key的数量也算参数，所以读取参数的索引要整体往右偏移一位（for i,k in pairs(KEYS) do redis.call('set', k, ARGV[i*2-1]); redis.call('PEXPIRE', k, ARGV[i*2]); end; return 1;
    public static final String luaScript3 = "for i,k in pairs(KEYS) do redis.call('set', k, ARGV[i*2]); redis.call('PEXPIRE', k, ARGV[i*2+1]); end; return 1;";
}
