package com.imooc.mall;

import com.imooc.mall.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by 廖师兄
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/error", "/user/login", "/user/register", "/categories", "/products", "/products/*", "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
                        "/api", "/api-docs", "/api-docs/**");
        //写好的拦截器需要通过这里添加注册才能生效 updated by hsoluo 20210125
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
