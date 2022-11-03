package com.aojiaodage.portal.config;

import com.aojiaodage.common.controller.CustomErrorController;
import com.aojiaodage.portal.interceptor.JwtAuthentication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableConfigurationProperties({ ServerProperties.class })
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ServerProperties serverProperties;

    WebMvcConfig(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Bean
    public DefaultErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes();
    }

    @Bean
    public BasicErrorController basicErrorController(ErrorAttributes errorAttributes) {
        return new CustomErrorController(
                errorAttributes,
                this.serverProperties.getError());
    }

    @Bean
    public JwtAuthentication JwtAuthentication() {
        return new JwtAuthentication();
    }

    // 大多数不需要登录【除了获取个人信息的接口&下单&支付等外
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(JwtAuthentication())
                .addPathPatterns("/**")
                .excludePathPatterns("/member/login", "/member/refresh", "/member/register", "/error", "/index/**", "/product/**", "/search/**", "/sku/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(false) // 不需要用cookie
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
