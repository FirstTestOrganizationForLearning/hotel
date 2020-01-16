package com.fntx.common.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.regex.Pattern;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: TokenCheck
 * @Author: 王俊文
 * @Date: 19-7-11 上午11:18
 * @Description: 分销商Token核验
 */

@Component
public class TokenCheck {

    /**
     * @Description: 核验分销商token
     * @Author: 王俊文
     * @Date: 19-7-11 上午11:52
     * @Param: [redisToken, accessToken]
     * @returns: java.lang.String  内容为 null 时核验失败
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-11 上午11:52          1.0
     */
    public static String distributorCheck(String redisToken, String accessToken) {
        //校验token是否一致
        if (redisToken.compareTo(accessToken) != 0) {
            //token校验失败
            return null;
        }
        //校验token是否为base64加密
        if ( !isBase64(redisToken) || !isBase64(accessToken))
        {
            return null;
        }
        //校验token明文,密文是否一致
        //解密token
//        java.util.Base64.getEncoder().encodeToString("进制)//编码
//                java.util.Base64.getDecoder().decode("字符串")//解码
        String[] plaintextRedisToken = new String(Base64.getDecoder().decode(redisToken)).split("_");
        String[] plaintextAccessToken = new String(Base64.getDecoder().decode(accessToken)).split("_");
        //reids token key
        String plaintextRedisTokenKey = plaintextRedisToken[0];
        //reids token moduleId
        String plaintextRedisTokenModuleId = plaintextRedisToken[1];
        //分销商 token key
        String plaintextAccessTokenKey = plaintextAccessToken[0];
        //分销商 token moduleId
        String plaintextAccessTokenModuleId = plaintextAccessToken[1];
        if ( plaintextRedisTokenKey.compareTo(plaintextAccessTokenKey) != 0 )
        {
            //token校验失败
            return null;
        }
        if ( plaintextRedisTokenModuleId.compareTo(plaintextAccessTokenModuleId) != 0 )
        {
            //token校验失败
            return null;
        }
        return plaintextAccessTokenModuleId;
    }


private static boolean isBase64(String str) {
    String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
    return Pattern.matches(base64Pattern, str);
}
}
