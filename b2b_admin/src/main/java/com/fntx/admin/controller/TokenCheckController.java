package com.fntx.admin.controller;

import com.fntx.admin.service.ITokenCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: TokenCheckController
 * @Author: 王俊文
 * @Date: 19-7-16 下午6:40
 * @Description: token相关
 */
@RestController
@RequestMapping(value = "/tokenCheck")
public class TokenCheckController
{
    private static final Logger logger = LoggerFactory.getLogger(TokenCheckController.class);
    @Autowired
    private ITokenCheckService iTokenCheckService;

    /**
     * @Description: token解析
     * @Author: 王俊文
     * @Date: 19-7-17 上午10:01
     * @Param: [redisToken, accessToken, hotelId]
     * @returns: java.lang.String
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-17 上午10:01          1.0          token解析
     */
    @GetMapping("")
    public String tokenCheck(@RequestParam(value="redisToken") String redisToken,
                                        @RequestParam(value="accessToken") String accessToken,
                                        @RequestParam(value="hotelId", required = false) String hotelId)
    {
        logger.info(
            "\n***************************************" + "\n" +
            "start get_tokenCheck" + "\n" +
            "token解析" + "\n" +
            "redisToken = " + redisToken + "--  redisToken\n" +
            "accessToken = " + accessToken+ "--  分销商传入accessToken\n" +
            "hotelId = " + hotelId+ "--  酒店id\n" +
            "\n********************************************"
        );
            String returnString =  iTokenCheckService.DistributorTokenCheck(redisToken, accessToken, hotelId);
        logger.info(
            "\n***************************************" + "\n" +
            "end get_tokenCheck" + "\n" +
            "token解析" + "\n" +
            "returnString = " + returnString + "\n" +
            "\n********************************************"
        );
        return returnString;
    }
}
