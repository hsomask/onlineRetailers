package com.springcloud.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author:hsoluo
 * @date 2021/2/5 15:30
 * 生成token的工具类
 */
public class TokenUtils {
//    private TokenUtils() {
//    }
//
//    ;
//    private static final TokenUtils instance = new TokenUtils();
//
//    public static TokenUtils getInstance() {
//        return instance;
//    }
//
//    /**
//     * 生成token
//     */
//    public String generateToken() {
//        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
//        try {
//
//            MessageDigest md = MessageDigest.getInstance("md5");
//            byte[] md5 = md.digest(token.getBytes());
//            BASE64Encoder encoder = new BASE64Encoder();
//            return encoder.encode(md5);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 生成token放入session
//     */
//
//    public static void setTokenIntoSession(HttpServletRequest request, String tokenServerkey) {
//        String token = getInstance().generateToken();
//        request.getSession().setAttribute(tokenServerkey, token);
//    }
//
//    /**
//     * 在session中移除
//     */
//
//    public static void removeTokenFromSession(HttpServletRequest request, String tokenServerkey) {
//        request.getSession().removeAttribute(tokenServerkey);
//    }
//
//    /**
//     * 判断请求参数中的token是否和session中一致
//     */
//    public static boolean judgeTokenIsEqual(HttpServletRequest request, String tokenClientkey, String tokenServerkey) {
//
//        String token_client = request.getParameter(tokenClientkey);
//
//        if (StringUtils.isEmpty(token_client)) {
//
//            return false;
//
//        }
//
//        String token_server = (String) request.getSession().getAttribute(tokenServerkey);
//
//        if (StringUtils.isEmpty(token_server)) {
//
//            return false;
//
//        }
//
//
//        if (!token_server.equals(token_client)) {
//
//            return false;
//
//        }
//
//
//        return true;
//
//    }
    /**
     * jwt
     */

    /**
     * token过期时间
     */
    private static final long EXPIRE_TIME = 30 * 60 * 1000;
    /**
     * token秘钥
     */
    private static final String TOKEN_SECRET = "436jlitreJLjkgalg256guiu";

    /**
     * 生成签名，30分钟过期
     *
     * @param username  用户名
     * @param loginTime 登录时间
     * @return 生成的token
     */

    public static String sign(String username, String loginTime) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            // 返回token字符串
            return JWT.create()
                    .withHeader(header)
                    .withClaim("loginName", username)
                    .withClaim("loginTime", loginTime)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检验token是否正确
     *
     * @param token 需要校验的token
     * @return 校验是否成功
     */
    public static boolean verify(String token) {
        try {
            //设置签名的加密算法：HMAC256
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
