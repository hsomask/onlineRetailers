package com.imooc.backOffice.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.imooc.backOffice.constant.ShiroConstant;
import com.imooc.backOffice.entity.AuthToken;
import com.imooc.backOffice.entity.UserEntity;
import com.imooc.backOffice.entity.UserRoleEntity;
import com.imooc.backOffice.mapper.RoleMapper;
import com.imooc.backOffice.utils.RedisUtil;
import com.imooc.backOffice.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author:hsoluo
 * @date 2021/3/15 9:31
 */
@Slf4j
@Component
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AuthToken;
    }

    //验证角色授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("开始角色授权的验证");
        SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();


        UserEntity user = (UserEntity) principalCollection.getPrimaryPrincipal();

        Long userId = user.getId();

        List<UserRoleEntity> list = roleMapper.findByUserId(userId);

        if (!list.isEmpty()) {
            list.forEach(o -> {
                authenticationInfo.addStringPermission(o.getRoleId().toString());
            });
        }

        return authenticationInfo;
    }

    //验证用户

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("验证用户。。。");
        String token = (String) authenticationToken.getCredentials();
        Long userId = TokenUtil.getField(token, "userId", Long.class);

        if (!RedisUtil.hasKey(ShiroConstant.LOGIN_SHIRO_CACHE + userId)) {
            throw new AuthenticationException("该用户登出或登录时间太长，请重新登录");
        }
        UserEntity user = (UserEntity) RedisUtil.get(ShiroConstant.LOGIN_SHIRO_CACHE + userId);

        //将redis中的用户缓存与token进行对比
        if (!user.getToken().equals(token)) {
            throw new AuthenticationException("token验证为不等，请重新登录！");
        }
        try {
            TokenUtil.verify(token, user.getUserName(), user.getId());
        } catch (JWTVerificationException e) {
            throw new AuthenticationException("token验证出错，" + e.getMessage());
        }

        return new SimpleAuthenticationInfo(user, token, this.getName());

    }
}
