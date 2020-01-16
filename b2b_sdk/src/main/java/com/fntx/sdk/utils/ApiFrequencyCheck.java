package com.fntx.sdk.utils;

import com.fntx.common.utils.TimeUtil;
import com.fntx.sdk.feign.AdminFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: ApiFrequencyCheck
 * @Author: 王俊文
 * @Date: 2019/8/30 下午3:14
 * @Description: 接口访问频次校验
 */
@Component
public class ApiFrequencyCheck
{
    private static Logger logger = LoggerFactory.getLogger(ApiFrequencyCheck.class);

    private static final String API_CONFIG_REDIS_KEY = "API_CONFIG_";

    @Autowired
    private AdminFeign adminFeign;
    @Autowired
    private CommonRedisHelper commonRedisHelper;
    @Autowired
    private RedisTemplate redisTemplate;

    public boolean apiFrequencyCheck(String compId, String sid, String iCode) throws Exception
    {
        String timeApiFrequency = TimeUtil.formatDate(new Date(), TimeUtil.YEAR_MONTH_DAY_TIME_BRANCH ) +
                "_ApiFrequency_";

        String lockKey = compId + "_" + sid + "_" + iCode;
        boolean sign = false, returnSign = false;
        boolean signLock = commonRedisHelper.lock(lockKey);
        if (signLock)
        {
            // 执行逻辑操作
            sign = true;
        } else {
            // 设置失败次数计数器, 当到达3次时, 返回失败
            int failCount = 1;
            while(failCount <= 3)
            {
                // 等待100ms重试
                try {
                    Thread.sleep(50l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (commonRedisHelper.lock(lockKey))
                {
                   // 执行逻辑操作
                    sign = true;
                    break;
                }else{
                    failCount ++;
                }
            }
            logger.info("现在创建的人太多了, 请稍等再试");
        }

        if ( sign )
        {
            redisTemplate.opsForValue().setIfAbsent( timeApiFrequency + lockKey, "0", 60*10,
            TimeUnit.SECONDS);
            Long value = redisTemplate.opsForValue().increment(timeApiFrequency+ lockKey, 1);

            //校验分销商超过访问频次后是否允许继续访问
            String key = API_CONFIG_REDIS_KEY + compId + "_" + sid;
            //缓存所有分销商频次配置信息
            //频次限制模式
            int retryNumber = 10;
            while (retryNumber>0)
            {
                try
                {
                    retryNumber--;
                    Object isExceedSingO = redisTemplate.opsForValue().get(key + "_isExceed");
                    Integer isExceedSign = -1;
                    if ( isExceedSingO != null )
                    {
                         isExceedSign = Integer.parseInt(isExceedSingO.toString());
                    }else
                    {
                        adminFeign.frequencyConfigInit();
                        continue;
                    }

                    //是否容许超过频次  0：不容许   1：容许
                    if ( isExceedSign.compareTo(0)  == 0)
                    {
                        Object valueObject = new Object();
                        switch (iCode)
                        {
                            case "hotel.baseprice":
                            {
                                //起价频次
                                 valueObject = redisTemplate.opsForValue().get(key + "_basePrice");
                                break;
                            }
                            case "hotel.ioprice":
                            {
                                //直连报价频次
                                valueObject = redisTemplate.opsForValue().get(key + "_directPrice");
                                break;
                            }
                            case "hotel.bookable.check":
                            {
                                //房型是否可预定频次
                                valueObject = redisTemplate.opsForValue().get(key + "_isBookroom");
                                break;
                            }
                            case "hotel.order.create":
                            {
                                //创建订单频次
                                valueObject = redisTemplate.opsForValue().get(key + "_createOrder");
                                break;
                            }
                            case "hotel.order.submit":
                            {
                                //提交订单频次
                                valueObject = redisTemplate.opsForValue().get(key + "_submitOrder");
                                break;
                            }
                            case "hotel.order.statechange":
                            {
                                //监测订单状态变化频次
                                valueObject = redisTemplate.opsForValue().get(key + "_orderstatusChange");
                                break;
                            }
                            case "hotel.order.detail":
                            {
                                //获取订单详情频次
                                valueObject = redisTemplate.opsForValue().get(key + "_orderDetail");
                                break;
                            }
                            case "hotel.order.cancel":
                            {
                                //取消订单频次
                                valueObject = redisTemplate.opsForValue().get(key + "_cancelOrder");
                                break;
                            }
                        }
                        Integer valueConfigure = -1;
                        if ( valueObject != null )
                        {
                             valueConfigure =  Integer.parseInt(valueObject.toString());
                        }else
                        {
                            adminFeign.frequencyConfigInit();
                            continue;
                        }
                        if ( value.compareTo(Long.parseLong(valueConfigure.toString())) == 1 )
                        {
                            returnSign = false;
                        }else
                        {
                            returnSign = true;
                            break;
                        }
                    }else
                    {
                        returnSign = true;
                        break;
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        commonRedisHelper.delete(lockKey);
        return returnSign;
    }
}
