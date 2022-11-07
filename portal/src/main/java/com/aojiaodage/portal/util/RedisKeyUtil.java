package com.aojiaodage.portal.util;

import com.aojiaodage.common.constant.RedisKey;

public class RedisKeyUtil {

    public static String getAccessTokenKey(String id) {
        return RedisKey.PORTAL + id + RedisKey.ACCESS_TOKEN;
    }

    public static String getRefreshTokenKey(String id) {
        return RedisKey.PORTAL + id + RedisKey.REFRESH_TOKEN;
    }

    public static String getPayloadKey(String id) {
        return RedisKey.PORTAL + id;
    }

    public static String getCartKey(Integer id) {
        return RedisKey.PORTAL_CART + id;
    }
}
