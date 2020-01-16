package com.fntx.admin.service.impl;

import com.fntx.admin.service.IHtDistributorTokenService;
import com.fntx.admin.utils.TokenUtil;
import com.fntx.common.constant.HtErrorType;
import com.fntx.common.constant.HtRedisExpireTime;
import com.fntx.common.domain.BaseReq;
import com.fntx.common.domain.ErrorResp;
import com.fntx.common.domain.TokenResp;
import com.fntx.common.utils.DistributorCheck;
import com.fntx.common.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 渠猛
 * @Date: 2019/7/10 0010 下午 3:32
 * @Description:
 */
@Service
public class HtDistributorTokenServiceImpl implements IHtDistributorTokenService {

    private static final Logger logger=  LoggerFactory.getLogger(HtDistributorTokenServiceImpl.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @Override
    public  TokenResp getDistributorToken(BaseReq req){

        //1.将参数进行权限认证
        BaseReq reqBase = DistributorCheck.distributorCheck(req);
        if (reqBase==null){
            return null;
        }
        //2.权限认证通过后,加密生成token,以及refresh_Token,并缓存到redis
        return getAndCacheToken(reqBase);


    }

    private TokenResp getAndCacheToken(BaseReq reqBase) {
        //2.权限认证通过后,加密生成token,以及refresh_Token
        //生成token
        String distributorToken = createDistributorToken(reqBase);
        if (!StringUtils.hasText(distributorToken)){
            return null;
        }
        //生成refresh token
        String distributorRefreshToken = createDistributorToken(reqBase);
        if (!StringUtils.hasText(distributorRefreshToken)){
            return null;
        }
        //将token与refreshToken加入缓存
        stringRedisTemplate.opsForValue()
                .set(TokenUtil.getHtTokenKey(reqBase.getAID(),reqBase.getSID(),reqBase.getUUID()),distributorToken, HtRedisExpireTime.TOKEN.getCode(), TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue()
                .set(TokenUtil.getHtRefreshTokenKey(reqBase.getAID(),reqBase.getSID()),distributorRefreshToken, HtRedisExpireTime.REFRESH_TOKEN.getCode(), TimeUnit.SECONDS);

        //3.返回数据
        TokenResp tokenResp = new TokenResp();
        tokenResp.setAID(reqBase.getAID());
        tokenResp.setSID(reqBase.getSID());
        tokenResp.setAccess_Token(distributorToken);
        tokenResp.setRefresh_Token(distributorRefreshToken);
        tokenResp.setExpires_In(HtRedisExpireTime.TOKEN.getCode());
        return tokenResp;
    }


    @Override
    public  String createDistributorToken(BaseReq req){

        logger.debug("createDistributorToken:参数:"+req);
        //最终返回值
        String encodedText=null;

        if (req!=null && StringUtils.hasText(req.getAID())
                && StringUtils.hasText(req.getSID()) && StringUtils.hasText(req.getModuleId())
                ){

            //1.md5 加密
            //明文
            String plaintext = req.getAID()+req.getSID()+req.getKEY()+Calendar.getInstance().getTimeInMillis()+new Random().nextInt();
            logger.debug("MD5明文:"+plaintext);
            //密文
            String ciphertext = MD5Util.md5(plaintext);
            logger.debug("MD5密文:"+plaintext);
            //2.base64 加密
            String plaintextBase64 = ciphertext+"_"+req.getModuleId();
            logger.debug("base64明文:"+plaintext);
            try {
                byte[] textByte = plaintextBase64.getBytes("UTF-8");
                encodedText = Base64.getEncoder().encodeToString(textByte);
                logger.debug("base64密文:"+encodedText);
            } catch (UnsupportedEncodingException e) {
                logger.error("com.fntx.admin.service.impl.HtDistributorTokenService:createDistributorToken:base64加密异常");
                throw new IllegalStateException("base64 加密异常");
            }
        }
        return encodedText;
    }

    @Override
    public Object refreshToken(BaseReq baseReq) throws IOException {

        ErrorResp resp=new ErrorResp();
        resp.setErrCode(HtErrorType.INVALID_PARAMETERS.getCode());
        resp.setErrMsg(HtErrorType.INVALID_PARAMETERS.getName());
        if (StringUtils.hasText(baseReq.getAID())&&
                StringUtils.hasText(baseReq.getSID())&&
                StringUtils.hasText(baseReq.getRefresh_Token())
                ){
            String refreshToken = stringRedisTemplate.opsForValue().get(TokenUtil.getHtTokenKey(baseReq.getAID(), baseReq.getSID(),baseReq.getUUID()));
            if (!StringUtils.hasText(refreshToken)){
                resp.setErrCode(HtErrorType.INVALID_REFRESHTOKEN.getCode());
                resp.setErrMsg(HtErrorType.INVALID_REFRESHTOKEN.getName());
                return resp;
            }
            //截取moduleId, 调用生成token
            byte[] bytes = Base64.getDecoder().decode(refreshToken);
            //MD5+"_"+moduleId 字符串 moduleId是10位数字
            String s= new String(bytes,"utf-8");
            String[] split = s.split("_");
            if (split.length==2){
                baseReq.setModuleId(split[1]);
            }
            return getAndCacheToken(baseReq);
        }else {
            return resp;
        }
    }
}
