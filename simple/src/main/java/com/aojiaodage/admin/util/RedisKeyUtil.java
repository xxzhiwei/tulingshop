package com.aojiaodage.admin.util;

import com.aojiaodage.common.constant.RedisKey;

public class RedisKeyUtil {

    public static String getAccessTokenKey(String id) {
        return RedisKey.ADMIN + id + RedisKey.ACCESS_TOKEN;
    }

    public static String getRefreshTokenKey(String id) {
        return RedisKey.ADMIN + id + RedisKey.REFRESH_TOKEN;
    }

    public static String getPayloadKey(String id) {
        return RedisKey.ADMIN + id;
    }
}
