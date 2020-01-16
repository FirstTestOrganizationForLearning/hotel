package com.fntx.sdk.controller;

import com.fntx.common.domain.BaseReq;
import com.fntx.common.utils.ValaditionUtils;
import com.fntx.sdk.feign.AdminFeign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 渠猛
 * @Date: 2019/8/1 10:18
 * @Description:  为分销商提供token接口
 */
@RestController
@RequestMapping("openserviceauth")
public class TokenController {

    @Autowired
    private AdminFeign adminFeign;
    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/8/1 13:12
     * @Description: 分销商获取token
     */
    @GetMapping("/authorize.ashx")
    public Object getToken(BaseReq baseReq){
        if (baseReq==null || StringUtils.isEmpty(baseReq.getAID()) || StringUtils.isEmpty(baseReq.getSID())){
            return ValaditionUtils.getParamError();
        }
     return adminFeign.getToken(baseReq);
    }

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/8/1 13:13
     * @Description: 分销商刷新token
     */
    @GetMapping("/refresh.ashx")
    public Object refreshToken(BaseReq baseReq){
        if (baseReq==null || StringUtils.isEmpty(baseReq.getAID()) || StringUtils.isEmpty(baseReq.getSID()) || StringUtils.isEmpty(baseReq.getRefresh_Token())){
            return ValaditionUtils.getParamError();
        }
        return adminFeign.refreshToken(baseReq);
    }
}
