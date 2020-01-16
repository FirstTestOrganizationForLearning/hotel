package com.fntx.admin.controller;

import com.fntx.admin.service.IHtDistributorTokenService;
import com.fntx.common.constant.HtErrorType;
import com.fntx.common.domain.BaseInfo;
import com.fntx.common.domain.BaseReq;
import com.fntx.common.domain.ErrorResp;
import com.fntx.common.domain.TokenResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 渠猛
 * @Date: 2019/7/10 0010 下午 3:26
 * @Description:
 */
@RestController
public class HtTokenController {

    private static final Logger logger = LoggerFactory.getLogger(HtTokenController.class);



    @Resource
    private IHtDistributorTokenService htDistributorTokenService;

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/7/10 0010 下午 4:20
     * @Description:
     * 分销商获取token
     */
    @RequestMapping(value = "/ht/token",method = RequestMethod.POST)
    public Object getDistributorToken(@RequestBody  BaseReq baseReq){
        ErrorResp resp=new ErrorResp();
        resp.setErrCode(HtErrorType.INVALID_PARAMETERS.getCode());
        resp.setErrMsg(HtErrorType.INVALID_PARAMETERS.getName());
        if (baseReq!=null){
            logger.debug("ht/token:入参:"+baseReq.toString());
        }else {
            return resp;
        }
        TokenResp distributorToken=null;
        try {
            distributorToken= htDistributorTokenService.getDistributorToken(baseReq);
        }catch (Exception e){
            logger.error("ht/token:异常信息:"+e.getMessage());
            return resp;
        }
        logger.debug("ht/token:返回值"+distributorToken);
        if (distributorToken==null){
            return resp;
        }
        return distributorToken;
    }
    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/7/10 0010 下午 4:21
     * @Description:
     * 分销商刷新token
     */
      
    @RequestMapping(value = "/ht/refreshToken",method = RequestMethod.POST)
    public Object refreshDistributorToken(@RequestBody BaseReq baseReq){
        ErrorResp resp=new ErrorResp();
        resp.setErrCode(HtErrorType.INVALID_PARAMETERS.getCode());
        resp.setErrMsg(HtErrorType.INVALID_PARAMETERS.getName());
        if (baseReq!=null){
            logger.debug("ht/refreshToken:入参:"+baseReq.toString());
        }else {
            return resp;
        }
        Object distributorToken=null;
        try {
            distributorToken= htDistributorTokenService.refreshToken(baseReq);
        }catch (Exception e){
            logger.error("ht/refreshToken:异常信息:"+e.getMessage());
            return resp;
        }
        logger.debug("ht/refreshToken:返回值"+distributorToken);
        if (distributorToken==null){
            return resp;
        }
        return distributorToken;
    }


}
