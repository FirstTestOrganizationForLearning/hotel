package com.fntx.admin.utils;

import com.alibaba.fastjson.JSONObject;
import com.fntx.common.constant.Enviroment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName:
 * @Author: 胡庆康
 * @Date: 2019/7/8 9:47
 * @Description:
 */
@Component
public class TokenUtil {
    /**
     * @Description: 根据当前环境获取token
     * @Author: 胡庆康
     * @Date: 2019/7/8 10:23
     * @Param: []
     * @returns: java.lang.String, 为null说明获取失败了
     * <author>     <time>             <version>       <desc>
     * 胡庆康      2019/7/8 10:23          1.0         根据当前环境获取token
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @Description: 调用获取token接口
     * @Author: 胡庆康
     * @Date: 2019/7/8 15:26
     * @Param: []
     * @returns: java.lang.String
     */
    @PostConstruct
    public JSONObject getToken() {
        System.out.println("我被执行了");
        String sid = "";
        String aid = "";
        String key = "";
        String appPath = "";
        String apiUrl = "";
        String overseasAid = "";
        String overseasSid = "";
        String overseasKey = "";
        String overseasAppPath = "";
        String overseasApiUrl = "";
        JSONObject result = new JSONObject();
        RestTemplate restTemplate = new RestTemplate();
        aid = Enviroment.AID.getValue();
        sid = Enviroment.SID.getValue();
        key = Enviroment.KEY.getValue();
        apiUrl = Enviroment.API_GetAccessToken_URL.getValue();
        appPath = Enviroment.URL.getValue();
        apiUrl = apiUrl.replace("{app_path}", appPath)
                .replace("{aid}", aid)
                .replace("{sid}", sid)
                .replace("{key}", key);

        //@TODO 此处为了测试使用的弗恩获取token的URL
        apiUrl = Enviroment.FN_GetToken_URL.getValue();
        overseasAid = Enviroment.OVERSEAS_AID.getValue();
        overseasSid = Enviroment.OVERSEAS_SID.getValue();
        overseasKey = Enviroment.OVERSEAS_KEY.getValue();
        overseasAppPath = Enviroment.URL.getValue();
        overseasApiUrl = Enviroment.API_GetAccessToken_URL.getValue().replace("{app_path}", overseasAppPath)
                .replace("{aid}", overseasAid)
                .replace("{sid}", overseasSid)
                .replace("{key}", overseasKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        //@TODO 先改国外为弗恩的url
        overseasApiUrl = apiUrl.replace("hotel.tianxiafen.com:82", "hotel.tianxiafen.com:83");
        JSONObject strBody = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, JSONObject.class).getBody();
        JSONObject overseasBody = restTemplate.exchange(overseasApiUrl, HttpMethod.GET, entity, JSONObject.class).getBody();
        result.put("strBody", strBody);
        result.put("overseasBody", overseasBody);
        if (strBody.containsKey("ErrCode") || overseasBody.containsKey("ErrCode")) {
            return result;
        }
        //token
        stringRedisTemplate.opsForValue().set("token", strBody.get("Access_Token").toString(), Long.parseLong(strBody.get("Expires_In").toString()), TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set("overseasToken", overseasBody.get("Access_Token").toString(), Long.parseLong(overseasBody.get("Expires_In").toString()), TimeUnit.SECONDS);
        //refreshToken
        stringRedisTemplate.opsForValue().set("refreshToken", strBody.get("Refresh_Token").toString());
        stringRedisTemplate.opsForValue().set("overseasRefreshToken", overseasBody.get("Refresh_Token").toString());
        return result;
    }

    /**
     * @Description: 调用刷新token接口
     * @Author: 胡庆康
     * @Date: 2019/7/8 15:25
     * @Param: []
     * @returns: boolean
     */
    public boolean refreshToken() {
        String sid = "";
        String aid = "";
        String key = "";
        String appPath = "";
        String apiUrl = "";
        String overseasAid = "";
        String overseasSid = "";
        String overseasKey = "";
        String overseasApiUrl = "";
        String overseasAppPath = "";
        JSONObject result = new JSONObject();
        String refreshToken = stringRedisTemplate.opsForValue().get("refreshToken");
        String overseasRefreshToken = stringRedisTemplate.opsForValue().get("overseasRefreshToken");
        RestTemplate restTemplate = new RestTemplate();
        if (Enviroment.IS_RELEASE_ENV) {
            sid = Enviroment.SID.getValue();
            aid = Enviroment.AID.getValue();
            key = Enviroment.KEY.getValue();
            apiUrl = Enviroment.API_RefreshToken_URL.getValue();
            appPath = Enviroment.URL.getValue();
            apiUrl = apiUrl.replace("{app_path}", appPath)
                    .replace("{aid}", aid)
                    .replace("{sid}", sid)
                    .replace("{key}", key)
                    .replace("{refresh_token}", refreshToken);
            //@TODO 此处为了测试使用的弗恩获取token的URL
            overseasAid = Enviroment.OVERSEAS_AID.getValue();
            overseasSid = Enviroment.OVERSEAS_SID.getValue();
            overseasKey = Enviroment.OVERSEAS_KEY.getValue();
            overseasApiUrl = Enviroment.API_GetAccessToken_URL.getValue().replace("{app_path}", appPath)
                    .replace("{aid}", overseasAid)
                    .replace("{sid}", overseasSid)
                    .replace("{key}", overseasKey);
        } else {
            sid = Enviroment.TEST_SID.getValue();
            aid = Enviroment.TEST_AID.getValue();
            key = Enviroment.TEST_KEY.getValue();
            apiUrl = Enviroment.API_RefreshToken_URL.getValue();
            appPath = Enviroment.TEST_URL.getValue();
            apiUrl = apiUrl.replace("{app_path}", appPath)
                    .replace("{aid}", aid)
                    .replace("{sid}", sid)
                    .replace("{key}", key)
                    .replace("{refresh_token}", refreshToken);
        }
        //@TODO 此处为了测试使用的弗恩获取token的URL
        apiUrl = Enviroment.FN_RefreshToken_URL.getValue();
        apiUrl = apiUrl.replace("{Refresh_Token}", refreshToken);
        overseasAid = Enviroment.OVERSEAS_AID.getValue();
        overseasSid = Enviroment.OVERSEAS_SID.getValue();
        overseasKey = Enviroment.OVERSEAS_KEY.getValue();
        overseasAppPath = Enviroment.URL.getValue();
        overseasApiUrl = Enviroment.API_RefreshToken_URL.getValue().replace("{app_path}", overseasAppPath)
                .replace("{aid}", overseasAid)
                .replace("{sid}", overseasSid)
                .replace("{key}", overseasKey)
                .replace("{refresh_token}", overseasRefreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        //@TODO 国外账号先替换为弗恩的
        overseasApiUrl = apiUrl.replace("hotel.tianxiafen.com:82", "hotel.tianxiafen.com:83");
        JSONObject body = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, JSONObject.class).getBody();
        JSONObject overseasBody = restTemplate.exchange(overseasApiUrl, HttpMethod.GET, entity, JSONObject.class).getBody();

        if (body.containsKey("ErrCode") || overseasBody.containsKey("ErrCode")) {
            return false;
        }
        if (Enviroment.IS_RELEASE_ENV) {
            stringRedisTemplate.opsForValue().set("token", body.get("Access_Token").toString(), Long.parseLong(body.get("Expires_In").toString()), TimeUnit.SECONDS);
            stringRedisTemplate.opsForValue().set("overseasToken", overseasBody.get("Access_Token").toString(), Long.parseLong(overseasBody.get("Expires_In").toString()), TimeUnit.SECONDS);
        } else {
            stringRedisTemplate.opsForValue().set("token", body.get("Access_Token").toString(), Long.parseLong(body.get("Expires_In").toString()), TimeUnit.SECONDS);
            stringRedisTemplate.opsForValue().set("overseasToken", overseasBody.get("Access_Token").toString(), Long.parseLong(overseasBody.get("Expires_In").toString()), TimeUnit.SECONDS);
        }
        stringRedisTemplate.opsForValue().set("refreshToken", body.get("Refresh_Token").toString());
        stringRedisTemplate.opsForValue().set("overseasRefreshToken", overseasBody.get("Refresh_Token").toString());
        return true;
    }

    //拼接酒店redis中的token的key
    public static String getHtTokenKey(String aid, String sid,String uuid) {
        return aid + ":" + sid +":"+uuid+ ":token";
    }

    //拼接酒店redis中的refreshtoken的key
    public static String getHtRefreshTokenKey(String aid, String sid) {
        return aid + ":" + sid +":"+"refreshToken";
    }

}
