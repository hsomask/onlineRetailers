package com.imooc.backOffice.mapper;

import com.imooc.backOffice.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author:hsoluo
 * @date 2021/3/12 17:12
 */
@Mapper
public interface UserMapper {


    @Select("select * from user where username=#{username,jdbcType=VARCHAR}")
    UserEntity findUserByUserName(@Param(value = "userName") String userName);


}
