package com.imooc.backOffice.mapper;

import com.imooc.backOffice.entity.UserEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author:hsoluo
 * @date 2021/3/12 17:12
 */
@Mapper
public interface UserMapper {


    @Select("select * from user where username=#{username,jdbcType=VARCHAR}")
    UserEntity findUserByUserName(@Param(value = "username") String username);


    @Select("select * from user where userId=#{userId,jdbcType=INTEGER}")
    UserEntity findUserByUserId(@Param(value = "userId") Long userId);


    @Insert("insert into user (username,password,token) values (#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR},#{token,jdbcType=VARCHAR})")
    int sign(UserEntity user);

    @Delete("delete user where userId=#{userId,jdbcType=INTEGER}")
    int deleteUserByUserId(@Param(value = "userId") Long userId);

    @Update("update user set token=#{token,jdbcType=VARCHAR} where userId=#{userId,jdbcType=INTEGER}")
    int updateTokenByUserId(@Param(value = "userId") Long userId, @Param(value = "token") String token);
}
