package com.imooc.backOffice.service;

import com.imooc.backOffice.constant.ShiroConstant;
import com.imooc.backOffice.entity.UserEntity;
import com.imooc.backOffice.mapper.RoleMapper;
import com.imooc.backOffice.mapper.UserMapper;
import com.imooc.backOffice.utils.RedisUtil;
import com.imooc.backOffice.utils.TokenUtil;
import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2RTFDTM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author:hsoluo
 * @date 2021/3/16 10:38
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;


    public UserEntity findUserByUserName(String username) {
        return userMapper.findUserByUserName(username);
    }


    //登录成功后，保存token到redis
    public String loginSuccess(UserEntity user) {

        String token = TokenUtil.sign(user.getUserName(), user.getId());
        user.setToken(token);
        RedisUtil.set(ShiroConstant.LOGIN_SHIRO_CACHE + user.getId(), user);
        return token;
    }


    public boolean sign(UserEntity user) {
        int save = userMapper.sign(user);
        return save > 0;
    }


    public boolean delUser(Long userId) {
        Optional<UserEntity> o = Optional.ofNullable(userMapper.findUserByUserId(userId));
        if (o.isPresent()) {

            userMapper.deleteUserByUserId(userId);
            roleMapper.deleteRoleByUserId(userId);
            RedisUtil.del(ShiroConstant.ROLE_SHIRO_CACHE + userId, ShiroConstant.LOGIN_SHIRO_CACHE + userId);
            return true;
        } else {
            return false;
        }
    }
}
