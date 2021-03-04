package com.imooc.mall;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * updated by hsoluo
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 不拦截路径
        List<String> irs = new ArrayList<String>();

        irs.add("/error");
        irs.add("/user/login");
        irs.add("/swagger-resources/**");
        irs.add("/webjars/**");
        irs.add("/v2/api-docs/**");
        irs.add("/v2/api-docs-ext/**");
        irs.add("/swagger-ui.html/**");
        irs.add("/api-docs/**");
        irs.add("/doc.html/**");
        irs.add("/service-worker.js/**");

        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(irs);

//        registry.addInterceptor(new UserLoginInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/error", "/user/login", "/swagger-resources/**", "/webjars/**", "/v2/api-docs/**", "/v2/api-docs-ext/**", "/swagger-ui.html/**", "/api/**", "/api-docs/**", "/service-worker.js/**", "/doc.html/**");
        //写好的拦截器需要通过这里添加注册才能生效 updated by hsoluo 20210125
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
