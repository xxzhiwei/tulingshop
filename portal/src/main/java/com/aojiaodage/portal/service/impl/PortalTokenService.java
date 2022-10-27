package com.aojiaodage.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.common.pojo.TokenDetail;
import com.aojiaodage.common.service.TokenService;
import com.aojiaodage.common.util.JwtUtil;
import com.aojiaodage.common.util.LuaUtil;
import com.aojiaodage.portal.dao.MemberDao;
import com.aojiaodage.portal.entity.Member;
import com.aojiaodage.portal.util.RedisKeyUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class PortalTokenService extends TokenService<Member> {

    // 避免循环依赖而不注入memberService【若注入service，使用@Lazy也能解决】
    @Autowired
    MemberDao memberDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public String getId(Member payload) {
        return payload.getId().toString();
    }

    @Override
    public Member getPayloadById(String id) {
        Member member = memberDao.selectById(id);
        if (member == null) {
            throw new CustomException("会员不存在，id：" + id);
        }
        return member;
    }

    @Override
    public void cache(TokenDetail<Member> tokenDetail) {
        Member payload = tokenDetail.getPayload();
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
    public Member parsePayload(String payload) {
        return JSON.parseObject(payload, Member.class);
    }

    @Override
    public long clear(String id) {
        return 0;
    }
}
