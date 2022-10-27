package com.aojiaodage.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.aojiaodage.admin.dao.UserDao;
import com.aojiaodage.admin.entity.User;
import com.aojiaodage.admin.util.RedisKeyUtil;
import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.common.pojo.TokenDetail;
import com.aojiaodage.common.service.TokenService;
import com.aojiaodage.common.util.JwtUtil;
import com.aojiaodage.common.util.LuaUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminTokenService extends TokenService<User> {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    UserDao userDao;

    @Override
    public String getId(User payload) {
        return payload.getId().toString();
    }

    @Override
    public User getPayloadById(String id) {
        User user = userDao.selectById(id);
        if (user == null) {
            throw new CustomException("用户不存在，id：" + id);
        }
        return user;
    }

    @Override
    public void cache(TokenDetail<User> tokenDetail) {
        User payload = tokenDetail.getPayload();
        String accessToken = tokenDetail.getAccessToken();
        String refreshToken = tokenDetail.getRefreshToken();
        String id = getId(payload);
        String accessTokenKey = RedisKeyUtil.getAccessTokenKey(id);
        String payloadKey = RedisKeyUtil.getPayloadKey(id);

        // 注意key的先后循序
        List<String> keys = new ArrayList<>(Arrays.asList(accessTokenKey, payloadKey));
        List<String> args = new ArrayList<>(Arrays.asList(accessToken, JwtUtil.TOKEN_TTL.toString(), JSON.toJSONString(payload), JwtUtil.TOKEN_TTL.toString()));

        if (StringUtils.hasText(refreshToken)) {
            String refreshTokenKey = RedisKeyUtil.getRefreshTokenKey(id);
            keys.add(refreshTokenKey);
            args.add(refreshToken);
            args.add(JwtUtil.REFRESH_TOKEN_TTL.toString());
        }

        args.add(0, String.valueOf(keys.size()));
        Object[] objects = args.toArray(new Object[]{});
        Long r = redisTemplate.execute(RedisScript.of(LuaUtil.luaScript3, Long.class), keys, objects);

        if (r == null || r != 1) {
            throw new CustomException("缓存令牌信息失败");
        }
    }

    @Override
    public String getRefreshToken(Claims claims) {
        String id = getId(claims);
        String refreshTokenKey = RedisKeyUtil.getRefreshTokenKey(id);
        return redisTemplate.opsForValue().get(refreshTokenKey);
    }

    @Override
    public List<String> getAccessTokenAndPayload(Claims claims) {
        String id = getId(claims);
        String refreshTokenKey = RedisKeyUtil.getAccessTokenKey(id);
        String payloadKey = RedisKeyUtil.getPayloadKey(id);
        List<String> keys = Arrays.asList(refreshTokenKey, payloadKey);
        List<?> r = redisTemplate.execute(RedisScript.of(LuaUtil.luaScript2, List.class), keys, String.valueOf(keys.size()));

        if (r == null) {
            throw new CustomException("执行lua脚本失败");
        }

        return r.stream().map(item -> item == null ? "" : item.toString()).collect(Collectors.toList());
    }

    @Override
    public User parsePayload(String payload) {
        return JSON.parseObject(payload, User.class);
    }

    @Override
    public long clear(String id) {
        String accessTokenKey = RedisKeyUtil.getAccessTokenKey(id);
        String payloadKey = RedisKeyUtil.getPayloadKey(id);
        String refreshTokenKey = RedisKeyUtil.getRefreshTokenKey(id);
        List<String> keys = Arrays.asList(accessTokenKey, payloadKey, refreshTokenKey);
        Long r = redisTemplate.execute(
                RedisScript.of(LuaUtil.luaScript1, Long.class),
                keys,
                String.valueOf(keys.size()));
        if (r == null) {
            throw new CustomException("执行lua脚本失败");
        }
        return r;
    }
}
