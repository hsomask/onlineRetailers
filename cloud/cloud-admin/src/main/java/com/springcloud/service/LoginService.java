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

    public boolean register(SysUser sysUser) {

        return true;
    }

}
