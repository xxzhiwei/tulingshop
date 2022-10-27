package com.aojiaodage.common.service;

import com.aojiaodage.common.enums.TokenIssuer;
import com.aojiaodage.common.exception.NotLoggedInException;
import com.aojiaodage.common.exception.TokenExpired;
import com.aojiaodage.common.exception.WrongTokenException;
import com.aojiaodage.common.pojo.TokenDetail;
import com.aojiaodage.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

public abstract class TokenService<T> {

    public TokenDetail<T> generate(T payload) {
        return generate(payload, true);
    }

    public TokenDetail<T> generate(T payload, boolean refreshTokenGenerated) {
        TokenDetail<T> tokenDetail = new TokenDetail<>();
        String accessToken = makeAccessToken(payload);
        tokenDetail.setAccessToken(accessToken);
        tokenDetail.setPayload(payload);

        if (refreshTokenGenerated) {
            String refreshToken = makeRefreshToken(payload);
            tokenDetail.setRefreshToken(refreshToken);
        }
        cache(tokenDetail);
        return tokenDetail;
    }

    // 规定：id从payload中获取
    public abstract String getId(T payload);

    public abstract T getPayloadById(String id);

    // subject即为创建token时所传入的id
    public String getId(Claims claims) {
        return claims.getSubject();
    }

    public String makeAccessToken(T payload) {
        String id = getId(payload);
        JwtBuilder jwtBuilder = JwtUtil.getJwtBuilder(id, JwtUtil.TOKEN_TTL, JwtUtil.getUUID(), TokenIssuer.ACCESS_TOKEN_ISSUER.name());
        return jwtBuilder.compact();
    }

    public String makeRefreshToken(T payload) {
        String id = getId(payload);
        JwtBuilder jwtBuilder = JwtUtil.getJwtBuilder(id, JwtUtil.REFRESH_TOKEN_TTL, JwtUtil.getUUID(), TokenIssuer.REFRESH_TOKEN_ISSUER.name());
        return jwtBuilder.compact();
    }

    public abstract void cache(TokenDetail<T> tokenDetail);

    // 从缓存中获取【如redis等】
    public abstract String getRefreshToken(Claims claims);

    public abstract List<String> getAccessTokenAndPayload(Claims claims);

    public T getPayload(String authorization) {

        if (!StringUtils.hasText(authorization)) {
            throw new NotLoggedInException();
        }

        // 格式应该为"Bearer token"
        String[] authorizationArr = authorization.split(" ");
        if (authorizationArr.length != 2) {
            throw new WrongTokenException();
        }

        String accessToken = authorizationArr[1];

        Claims claims = JwtUtil.parseJWT(accessToken);
        String issuer = claims.getIssuer();

        // 防止使用refreshToken当做accessToken使用
        if (!TokenIssuer.ACCESS_TOKEN_ISSUER.name().equals(issuer)) {
            throw new WrongTokenException();
        }
        List<String> accessTokenAndPayload = getAccessTokenAndPayload(claims);

        String token = accessTokenAndPayload.get(0);
        String payload = accessTokenAndPayload.get(1);

        if (!StringUtils.hasText(token) || !StringUtils.hasText(payload)) {
            throw new NotLoggedInException();
        }

        // 两者比较
        if (!token.equals(accessToken)) {
            throw new NotLoggedInException();
        }
        return parsePayload(payload);
    }

    public abstract T parsePayload(String payload);

    public TokenDetail<T> refresh(String refreshToken) {
        Claims claims = JwtUtil.parseJWT(refreshToken);

        // 校验是否为refreshToken
        if (!TokenIssuer.REFRESH_TOKEN_ISSUER.name().equals(claims.getIssuer())) {
            throw new WrongTokenException();
        }
        String id = getId(claims);

        // 根据claims获取与传入的refreshToken比较的另一个refreshToken
        String _refreshToken = getRefreshToken(claims);

        // 若_refreshToken不存在或者与refreshToken不想等，则认为是过期的
        if (!StringUtils.hasText(_refreshToken) || !_refreshToken.equals(refreshToken)) {
            throw new TokenExpired();
        }

        T payload = getPayloadById(id);

        Date expiration = claims.getExpiration();
        long currentTimeMillis = System.currentTimeMillis();

        // refreshToken即将过期时，重新颁发一个
        boolean refreshTokenExpired = (expiration.getTime() - currentTimeMillis) < JwtUtil.TOKEN_TTL * 6;

        return generate(payload, refreshTokenExpired);
    }

    // 根据id清除缓存
    public abstract void clear(String id);
}
