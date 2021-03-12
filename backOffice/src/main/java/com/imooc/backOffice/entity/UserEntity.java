package com.imooc.backOffice.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author:hsoluo
 * @date 2021/3/12 17:00
 */
@Data
@Entity
//@Table(name = "user")

public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String userName;
    String password;

    @Transient
    String token;
}
