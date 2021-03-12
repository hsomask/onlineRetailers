package com.imooc.backOffice.mapper;

import com.imooc.backOffice.entity.UserEntity;
import com.imooc.backOffice.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author:hsoluo
 * @date 2021/3/12 17:12
 */
@Mapper
public interface RoleMapper {


    @Select("select * from user_role where userId=#{userId,jdbcType=INTEGER}")
    List<UserRoleEntity> findByUserId(@Param(value = "userId") Long userId);


}
