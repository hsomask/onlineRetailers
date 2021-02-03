package com.springcloud.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 描述:jwt 模型
 *
 * @Auther: hsoluo
 * @Date: 2021年2月3日20:57:20
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtModel {

    private String userName;

    private List<String> roleIdList;

}
