package com.springcloud.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author:hsoluo
 * @date 2021/2/5 14:51
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseModel {
    private Long id;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;
}
