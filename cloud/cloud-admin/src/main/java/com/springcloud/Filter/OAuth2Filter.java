//package com.springcloud.Filter;
//
//import com.alibaba.fastjson.JSONObject;
//import com.springcloud.dao.HttpResult;
//import com.springcloud.dao.HttpStatus;
//import com.springcloud.dao.OAuth2Token;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * Oauth2过滤器
// * 拦截除配置成不需认证的请求路径外的请求，
// * 都交由这个过滤器处理，负责接收前台带过来的token并封装成对象，如果请求没有携带token，则提示错误。
// *
// * @author:hsoluo
// * @date 2021/2/5 10:56
// */
//@Slf4j
//public class OAuth2Filter extends AuthenticatingFilter {
//    @Override
//    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
//
////        获取请求的token
//        String token = getRequestToken((HttpServletRequest) request);
//        if (StringUtils.isBlank(token)) {
//            return null;
//        }
//        return new OAuth2Token(token);
//    }
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        return false;
//    }
//
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//        // 获取请求token，如果token不存在，直接返回401
//        String token = getRequestToken((HttpServletRequest) request);
//        if (StringUtils.isBlank(token)) {
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            HttpResult result = HttpResult.error(HttpStatus.SC_UNAUTHORIZED, "invalid token");
//            String json = JSONObject.toJSONString(result);
//            httpResponse.getWriter().print(json);
//            return false;
//        }
//        return executeLogin(request, response);
//    }
//
//    @Override
//    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.setContentType("application/json; charset=utf-8");
//
//        try {
//            // 处理登录失败的异常
//            Throwable throwable = e.getCause() == null ? e : e.getCause();
//            HttpResult result = HttpResult.error(HttpStatus.SC_UNAUTHORIZED, throwable.getMessage());
//            String json = JSONObject.toJSONString(result);
//            httpResponse.getWriter().print(json);
//
//        } catch (Exception el) {
//        }
//        return false;
//    }
//
//    /**
//     * 获取请求的token
//     */
//    private String getRequestToken(HttpServletRequest request) {
//        String token = request.getHeader("token");
//        // 如果header中不存在token，则从参数中获取token
//        if (StringUtils.isBlank(token)) {
//            token = request.getParameter("token");
//        }
//        return token;
//    }
//}
