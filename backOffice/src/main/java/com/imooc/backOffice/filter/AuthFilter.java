package com.imooc.backOffice.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.imooc.backOffice.constant.ShiroConstant;
import com.imooc.backOffice.entity.AuthToken;
import com.imooc.backOffice.entity.UserEntity;
import com.imooc.backOffice.utils.RedisUtil;
import com.imooc.backOffice.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author:hsoluo
 * @date 2021/3/15 11:39
 */
@Slf4j
@Component
public class AuthFilter extends AuthenticatingFilter {
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return new AuthToken(token);

    }

    /**
     * 步骤1.拦截请求并验证token,成功则进行授权，否则进入onAccessDenied方法
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        AuthToken jwtToken = (AuthToken) this.createToken(request, response);
        if (jwtToken != null) {
            try {

                String token = jwtToken.getToken();
                // 提交给realm进行登入，如果错误他会抛出异常并被捕获
                // 如果没有抛出异常则代表登入成功，返回true
                getSubject(request, response).login(jwtToken);

                //判断是否要更新token

                String refreshToken = this.refreshToken(token);

                if (!StringUtils.isEmpty(refreshToken)) {
                    log.info("更新token时间");

                    UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
                    user.setToken(refreshToken);
                    //更新redis中用户对象的token
                    RedisUtil.set(ShiroConstant.LOGIN_SHIRO_CACHE + user.getId(), user);
                    //将响应结果加上更新后的token
                    HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
                    httpServletResponse.setHeader("token", refreshToken);
                    httpServletResponse.setHeader("Access-Control-Expose-Headers", "token");

                }


                return true;

            } catch (AuthenticationException e) {
                log.error(e.getLocalizedMessage());
                return false;
            }
        } else {
            return false;
        }


    }

    /**
     * 步骤2，若验证成功则进行业务操作，false直接返回。我这边的流程在		        isAccessAllowed已经处理完了，所以这里直接返回false。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }


    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isEmpty(token)) {
            token = httpServletRequest.getParameter("token");
        }
        return token;
    }


    /**
     * 更新token
     */

    private String refreshToken(String token) {
        String sign = null;
        DecodedJWT jwt = JWT.decode(token);
        //获取过期时间
        Date expireTime = jwt.getExpiresAt();

        boolean isRefresh = (expireTime.getTime() - System.currentTimeMillis()) < TokenUtil.USED_TIME;
        if (isRefresh) {
            Long userId = TokenUtil.getField(token, "userId", Long.class);
            String userName = TokenUtil.getField(token, "userName", String.class);
            sign = TokenUtil.sign(userName, userId);
        }
        return sign;
    }
}
