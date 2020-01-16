package com.fntx.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.fntx.common.constant.BusiModuleType;
import com.fntx.common.constant.Enviroment;
import com.fntx.common.domain.BaseReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: TokenCheck
 * @Author: 王俊文
 * @Date: 19-7-9 上午9:28
 * @Description: 分销商校验
 */
@Component
public class DistributorCheck
{
    private static final Logger logger = LoggerFactory.getLogger(DistributorCheck.class);

    public static BaseReq distributorCheck(BaseReq baseReq)
    {
        //时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());

        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "";
        if ( Enviroment.IS_RELEASE_ENV )
        {
            apiUrl = Enviroment.DISTRIBUTOR_CHECK_URL.getValue();
        }else
        {
            apiUrl = Enviroment.TEST_DISTRIBUTOR_CHECK_URL.getValue();
        }

        Map<String, String> param = new HashMap<>(16);
        param.put("aid", baseReq.getAID());
        param.put("moduleId", baseReq.getModuleId());
        param.put("sid", baseReq.getSID());
        param.put("uuid", baseReq.getUUID());
        param.put("key", baseReq.getKEY());

        //sign
        String sign = "";
        if ( baseReq.getModuleId() != null && !"".equals(baseReq.getModuleId()) )
        {
            sign = MD5Util.encrypt(baseReq.getAID() + baseReq.getModuleId() + timestamp + "hotel");
        }else
        {
            sign = MD5Util.encrypt(baseReq.getAID() + timestamp + "hotel");
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("timestamp", timestamp);
        headers.set("sign", sign);
        HttpEntity<Map<String, String>> formEntity = new HttpEntity<Map<String, String>>(param, headers);
        Map<String, Object> response = restTemplate.exchange(apiUrl, HttpMethod.POST, formEntity, Map.class).getBody();

        logger.info(response.values().toString());
        if ( response.get("status").equals(100) )
        {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(response.get("data")));
            //判断是何种酒店 国内酒店：1   国际酒店：2
            //本系统 0：国内   1：国际
            if ( jsonObject.getString("type").compareTo("1") == 0 )
            {
                //0：国内
                baseReq.setModuleId("0");
            }else if ( jsonObject.getString("type").compareTo("2") == 0 )
            {
                //1：国际
                baseReq.setModuleId("1");
            }

            baseReq.setUUID(jsonObject.getString("uuid"));
            baseReq.setUrl(jsonObject.getString("paymentcallbackurl"));
            return baseReq;
        }
        return null;
    }

    public static void main(String[] args) {
        BaseReq baseReq = new BaseReq();
        baseReq.setAID("test");
        baseReq.setSID("123");
        baseReq.setUUID("123");
        baseReq.setKEY("123");
        baseReq.setModuleId(BusiModuleType.INTERNATIONAL_HOTEL.getCode());
        System.out.println(distributorCheck(baseReq));
    }
}
