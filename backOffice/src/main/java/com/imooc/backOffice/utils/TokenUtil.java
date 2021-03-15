package com.imooc.backOffice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:hsoluo
 * @date 2021/3/15 10:31
 */
@Component
@Slf4j
public class TokenUtil {

    //Token过期时间
    private static final long EXPIRE_TIME = 10 * 60 * 1000;

    private static final String TOKEN_SECRET = "lzm234tCl...";

    //当前时间与过期时间差小于这个时间，token刷新
    public static final long USED_TIME = 9 * 1000 * 60;

    /**
     * 生成签名
     * 正常Token：Token未过期，且未达到建议更换时间。
     * 濒死Token：Token未过期，已达到建议更换时间。
     * 正常过期Token：Token已过期，但存在于缓存中。
     * 非正常过期Token：Token已过期，不存在于缓存中
     *
     * @param **username**
     * @param **password**
     * @return String
     */

    public static String sign(String userName, Long userId) {
        try {
            // 设置过期时间
            // 私钥和加密算法
            // 设置头部信息

            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS512");
            return JWT.create()
                    .withHeader(header)
                    .withClaim("userName", userName)
                    .withClaim("userId", userId)
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                    .sign(Algorithm.HMAC512(TOKEN_SECRET));

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }
    }


    public static boolean verify(String token, String userName, Long userId) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC512(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("userName", userName)
                .withClaim("userId", userId)
                .build();
        return true;
    }

    //获取token中的数据，不需要解密
    public static <T> T getField(String token, String field, Class<T> clazz) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim(field).as(clazz);
    }

}
