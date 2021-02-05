//package com.springcloud.config;
//
//import com.springcloud.Filter.OAuth2Filter;
//import org.apache.shiro.mgt.DefaultSecurityManager;
//import org.apache.shiro.realm.Realm;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.apache.shiro.mgt.SecurityManager;
//
//import javax.servlet.Filter;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * author:hsoluo
// * date:2021年2月5日10:18:26
// * 注入自定义的认证过滤器（OAuth2Filter）和认证器（OAuth2Realm），并添加请求路径拦截配置
// */
//@Configuration
//public class ShiroConfig {
//    @Bean
//    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
//
//        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
//        shiroFilter.setSecurityManager(securityManager);
//        // 自定义 OAuth2Filter 过滤器，替代默认的过滤器
//        Map<String, Filter> filters = new HashMap<>();
//        filters.put("oauth2", new OAuth2Filter());
//        shiroFilter.setFilters(filters);
//        // 访问路径拦截配置，"anon"表示无需验证，未登录也可访问
//        Map<String, String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/webjars/**", "anon");
//
//        //首页和登录页面
//
//
//        //swagger
//        filterMap.put("/swagger-ui.html", "anon");
//        filterMap.put("/swagger-resources", "anon");
//        filterMap.put("/v2/api-docs", "anon");
//        filterMap.put("/webjars/springfox-swagger-ui/**", "anon");
//        // 其他所有路径交给OAuth2Filter处理
//        filterMap.put("/**", "oauth2");
//        shiroFilter.setFilterChainDefinitionMap(filterMap);
//        return shiroFilter;
//    }
//
//    @Bean
//    public Realm getShiroRealm() {
//        OAuth2Realm oAuth2Realm = new OAuth2Realm();
//        return oAuth2Realm;
//    }
//
//    @Bean
//    public SecurityManager securityManager() {
//        DefaultSecurityManager securityManager = new DefaultSecurityManager();
//        // 注入 Realm 实现类，实现自己的登录逻辑
//        securityManager.setRealm(getShiroRealm());
//        return securityManager;
//    }
//}
