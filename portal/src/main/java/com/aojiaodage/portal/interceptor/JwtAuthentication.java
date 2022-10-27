package com.aojiaodage.portal.interceptor;

import com.alibaba.fastjson.JSON;
import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.common.exception.NotLoggedInException;
import com.aojiaodage.common.util.R;
import com.aojiaodage.common.util.RespUtil;
import com.aojiaodage.portal.entity.Member;
import com.aojiaodage.portal.service.impl.PortalTokenService;
import com.aojiaodage.portal.util.PayloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthentication implements HandlerInterceptor {

    @Autowired
    PortalTokenService tokenService;

    public static final String AUTHORIZATION = "Authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();

        // 若为options方法时，直接放行
        if (HttpMethod.OPTIONS.name().equals(method)) {
            return true;
        }

        String authorization = request.getHeader(AUTHORIZATION);

        try {
            Member payload = tokenService.getPayload(authorization);
            PayloadUtil.set(payload);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            R<?> r;
            if (exception instanceof CustomException) {
                CustomException ex = (CustomException) exception;
                r = new R<>(ex.getCode(), ex.getMessage());
            }
            else {
                r = R.error();
                r.setCode(NotLoggedInException.CODE);
                r.setMessage("令牌已过期，请重新登录");
            }

            RespUtil.output(response, JSON.toJSONString(r), HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        PayloadUtil.remove();
    }
}
