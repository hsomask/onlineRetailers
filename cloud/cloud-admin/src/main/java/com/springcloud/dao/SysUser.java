package com.springcloud.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author:hsoluo
 * @date 2021/2/5 14:54
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SysUser extends BaseModel {

    private String username;
    private String password;
    private String salt;
    private String email;
    private String mobile;
    private Byte status;
    private Byte delFlag;
    //暂时将token存在数据库中
//    private String token;

    private Date loginTime;
}
