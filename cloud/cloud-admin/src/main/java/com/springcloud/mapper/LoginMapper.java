package com.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.dao.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * @author:hsoluo
 * @date 2021/2/5 17:13
 */
@Mapper
public interface LoginMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户名查该用户信息
     */
    @Select("select * from mall.mall_user where username=#{username,jdbcType=VARCHAR}")
    public SysUser getUserByUsername(@Param("username") String username);

    @Insert("insert into mall.mall_user ('username','password','salt','email','mobile')  " +
            "values(#{username,jdbcType=VARCHAR},#{password,jdbcType=VARCHAR}," +
            "#{salt,jdbcType=VARCHAR},#{email,jdbcType=VARCHAR},#{mobile,jdbcType=VARCHAR})")
    public int addUser(SysUser sysUser);

    @Select("select * from mall.mall_user where mobile=#{mobile,jdbcType=VARCHAR}")
    public SysUser getUserByMobile(@Param("mobile") String mobile);
}
