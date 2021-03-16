package com.imooc.backOffice.service;

import com.imooc.backOffice.constant.ShiroConstant;
import com.imooc.backOffice.entity.UserEntity;
import com.imooc.backOffice.mapper.RoleMapper;
import com.imooc.backOffice.mapper.UserMapper;
import com.imooc.backOffice.utils.RedisUtil;
import com.imooc.backOffice.utils.TokenUtil;
import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2RTFDTM;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @author:hsoluo
 * @date 2021/3/16 10:38
 */
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;


    public UserEntity findUserByUserName(String userName) {
        return userMapper.findUserByUserName(userName);
    }


    //登录成功后，保存token到redis
    public String loginSuccess(UserEntity user) {

        String token = TokenUtil.sign(user.getUserName(), user.getId());
        user.setToken(token);
        RedisUtil.set(ShiroConstant.LOGIN_SHIRO_CACHE + user.getId(), user);
        return token;
    }


    public boolean saveUser(UserEntity user) {
        int save = userMapper.sign(user);
        return save > 0;
    }


    public boolean delUser(UserEntity user) {
        Optional<UserEntity> o = Optional.ofNullable(userMapper.findUserByUserId(user.getId()));
        if (o.isPresent()) {

            userMapper.deleteUserByUserId(user.getId());
            roleMapper.deleteRoleByUserId(user.getId());
            RedisUtil.del(ShiroConstant.ROLE_SHIRO_CACHE + user.getId(), ShiroConstant.LOGIN_SHIRO_CACHE + user.getId());
            return true;
        } else {
            return false;
        }
    }
}
