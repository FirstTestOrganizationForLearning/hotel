package com.fntx.admin.service;

import com.fntx.common.domain.BaseReq;
import com.fntx.common.domain.TokenResp;

import java.io.IOException;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 渠猛
 * @Date: 2019/7/10 0010 下午 3:24
 * @Description:
 * 分销商获取token,刷新token
 */
public interface IHtDistributorTokenService {

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/7/10 0010 上午 9:30
     * @Description:
     * 获取分销商token
     * 如果验证不通过,返回null,
     * 验证通过
     * 拼接Token ,加密
     */
    TokenResp getDistributorToken(BaseReq baseReq);


    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/7/10 0010 上午 10:43
     * @Description:
     * 生成酒店分销商的token以及refresh_token
     * 加密规则:
     *  AID+SID+KEY+时间戳 通过md5加密,然后将ModuleId拼接在得到的字符串后面,之后通过base64再次加密得到token
     */
    String createDistributorToken(BaseReq req);



    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/7/10 0010 下午 4:25
     * @Description:
     * 刷新token
     */
    Object refreshToken(BaseReq baseReq) throws IOException;
}
