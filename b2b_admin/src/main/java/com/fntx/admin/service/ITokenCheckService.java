package com.fntx.admin.service;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: ITokenCheckService
 * @Author: 王俊文
 * @Date: 19-7-11 下午12:05
 * @Description: 分销商token核验
 */
public interface ITokenCheckService
{
    String DistributorTokenCheck(String redisToken, String accessToken, String hotelId);
}
