package com.springcloud.service;

import com.springcloud.dao.SysUser;
import com.springcloud.mapper.LoginMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author:hsoluo
 * @date 2021/2/5 17:08
 */
@Service
@Slf4j
public class LoginService {
    @Resource
    private LoginMapper loginMapper;

    /**
     * 注册
     * 首先要判断数据库中是否有此用户，如果有的话，就提示已有此用户
     * 如果没有的话，就进行注册流程
     * @param sysUser
     * @return
     */
    public boolean register(SysUser sysUser) {

        return true;
    }

}
