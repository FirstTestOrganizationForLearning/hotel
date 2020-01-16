package com.fntx.admin.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fntx.admin.service.IBFrequencyLogService;
import com.fntx.common.dao.BFrequencyLogMapper;
import com.fntx.common.domain.BFrequencyLog;
import com.fntx.common.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: BFrequencyConfigTask
 * @Author: 王俊文
 * @Date: 2019/9/2 上午10:11
 * @Description: 访问记录定时入库
 */
@Component
public class BFrequencyConfigTask
{
    private static final Logger logger = LoggerFactory.getLogger(BFrequencyConfigTask.class);

    private static final String API_CONFIG_REDIS_KEY = "API_CONFIG_";

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private BFrequencyLogMapper bFrequencyLogMapper;
    @Autowired
    private IBFrequencyLogService ibFrequencyLogService;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void bFrequencyConfigTask()
    {
        logger.info("分销商访问记录入库");
        //redis获取所有访问记录内容
        Date date = new Date();
        //获取当前时间的分钟
        String currentTime = TimeUtil.formatDate(date, TimeUtil.YEAR_MONTH_DAY_TIME_BRANCH);
        //获取当前时间的前一分钟
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, -1);
        String beforeTime = TimeUtil.formatDate(calendar.getTime(), TimeUtil.YEAR_MONTH_DAY_TIME_BRANCH);

        List<String> keyList = new ArrayList<>(16);
        while (true)
        {
            try {
             Cursor<String> cursor = scan(redisTemplate, currentTime + "_ApiFrequency_*", 1000);
             while (cursor.hasNext())
             {
                 String redisKey = cursor.next();
                 keyList.add(redisKey);
             }
             Cursor<String> cursor2 = scan(redisTemplate, beforeTime + "_ApiFrequency_*", 1000);
             while (cursor2.hasNext())
             {
                 String redisKey = cursor2.next();
                 keyList.add(redisKey);
             }
             //关闭scan
             cursor2.close();
             break;
            } catch (Exception e) {
             e.printStackTrace();
             continue;
            }
        }

        JSONArray jsonArray = new JSONArray(16);
        //访问记录入库
        for (String s : keyList )
        {
            boolean sign = false;
            //解析时间  分销商id
            String[] compIdTime = s.split("_");
            String time = compIdTime[0];
            String compId = compIdTime[2];
            String icode = compIdTime[3];
            Object o = redisTemplate.opsForValue().get(s);
            JSONObject apiNumJson = new JSONObject(16);
            apiNumJson.put("icode", icode);
            apiNumJson.put("number", o);
            for ( int i =0; i<jsonArray.size(); i++)
            {
                if ( time.equals(jsonArray.getJSONObject(i).get("time")) )
                {
                    JSONArray contentJsonArray = jsonArray.getJSONObject(i).getJSONArray("content");
                    for ( int j = 0; j < contentJsonArray.size(); j++)
                    {
                         if ( compId.equals(contentJsonArray.getJSONObject(j).getString("compId")) )
                        {
                            contentJsonArray.getJSONObject(j).getJSONArray("api").add(apiNumJson);
                            sign = true;
                        }
                    }
                    if ( !sign )
                    {
                        JSONObject jsonObject = new JSONObject(16);
                        jsonObject.put("compId", compId);
                        JSONArray jsonArray1 = new JSONArray(16);
                        jsonArray1.add(apiNumJson);
                        jsonObject.put("api", jsonArray1);
                        contentJsonArray.add(jsonObject);
                        sign = true;
                    }
                }
            }


            if ( !sign )
            {
                //没有已存在符合的记录
                //时间节点
                JSONObject jsonObject = new JSONObject(16);
                jsonObject.put("time", time);
                //内容节点(对应时间下分销商数组)
                JSONArray timeCompArray = new JSONArray(16);
                //当前分销商节点(分销商id 及接口数据数组)
                JSONObject timeCompJson = new JSONObject(16);
                timeCompJson.put("compId", compId);
                //分销商对应接口数据数组
                JSONArray timeCompApiArray = new JSONArray(16);
                timeCompApiArray.add(apiNumJson);
                timeCompJson.put("api", timeCompApiArray);
                timeCompArray.add(timeCompJson);
                jsonObject.put("content", timeCompArray);
                jsonArray.add(jsonObject);
            }
        }

        List<BFrequencyLog> bFrequencyLogList = new ArrayList<>(16);
        for ( int i = 0; i< jsonArray.size(); i++)
        {
            String time = jsonArray.getJSONObject(i).getString("time");
            JSONArray timeCompArray = jsonArray.getJSONObject(i).getJSONArray("content");
            if ( timeCompArray != null )
            {
                for ( int j = 0; j<timeCompArray.size(); j++)
                {
                    BFrequencyLog bFrequencyLog  = new BFrequencyLog();
                    String compId = timeCompArray.getJSONObject(j).getString("compId");
                    JSONArray timeCompApiArray = timeCompArray.getJSONObject(j).getJSONArray("api");
                    String key = API_CONFIG_REDIS_KEY + compId;
                    //日志id
                    bFrequencyLog.setCompidTimeId(time+compId);
                    //分销商id
                    bFrequencyLog.setCompid(compId);
                    //创建时间
                    bFrequencyLog.setCreateTime(new Date());
                    //更新时间
                    bFrequencyLog.setUpdateTime(new Date());
                    //获取各接口详细内容
                    for ( int k = 0; k<timeCompApiArray.size(); k++)
                    {
                        JSONObject apiNumJson = timeCompApiArray.getJSONObject(k);
                        switch (apiNumJson.getString("icode"))
                        {
                            case "hotel.baseprice":
                                {
                                    //起价频次
                                    //访问频次
                                    bFrequencyLog.setBasePrice(apiNumJson.getLong("number"));
                                    //超限频次
                                    Long exceedNum = Long.valueOf(redisTemplate.opsForValue().get(key+"_basePrice").toString());
                                    Long exceedNumCont = Long.valueOf(
                                            new BigDecimal(bFrequencyLog.getBasePrice().toString()).subtract(new BigDecimal(exceedNum.toString())).toString());
                                    bFrequencyLog.setBasePriceExceed(exceedNumCont>0?exceedNumCont:0);
                                    break;
                                }
                                case "hotel.ioprice":
                                {
                                    //直连报价频次
                                    //访问频次
                                    bFrequencyLog.setDirectPrice(apiNumJson.getLong("number"));
                                    //超限频次
                                    Long exceedNum = Long.valueOf(redisTemplate.opsForValue().get(key+"_directPrice").toString());
                                    Long exceedNumCont = Long.valueOf(
                                            new BigDecimal(bFrequencyLog.getDirectPrice().toString()).subtract(new BigDecimal(exceedNum.toString())).toString());
                                    bFrequencyLog.setDirectPriceExceed(exceedNumCont>0?exceedNumCont:0);
                                    break;
                                }
                                case "hotel.bookable.check":
                                {
                                    //房型是否可预定频次
                                    //访问频次
                                    bFrequencyLog.setIsBookroom(apiNumJson.getLong("number"));
                                    //超限频次
                                    Long exceedNum = Long.valueOf(redisTemplate.opsForValue().get(key+"_isBookroom").toString());
                                    Long exceedNumCont = Long.valueOf(
                                            new BigDecimal(bFrequencyLog.getIsBookroom().toString()).subtract(new BigDecimal(exceedNum.toString())).toString());
                                    bFrequencyLog.setIsBookroomExceed(exceedNumCont>0?exceedNumCont:0);
                                    break;
                                }
                                case "hotel.order.create":
                                {
                                    //创建订单频次
                                    //访问频次
                                    bFrequencyLog.setCreateOrder(apiNumJson.getLong("number"));
                                    //超限频次
                                    Long exceedNum = Long.valueOf(redisTemplate.opsForValue().get(key+"_createOrder").toString());
                                    Long exceedNumCont = Long.valueOf(
                                            new BigDecimal(bFrequencyLog.getCreateOrder().toString()).subtract(new BigDecimal(exceedNum.toString())).toString());
                                    bFrequencyLog.setCreateOrderExceed(exceedNumCont>0?exceedNumCont:0);
                                    break;
                                }
                                case "hotel.order.submit":
                                {
                                    //提交订单频次
                                    //访问频次
                                    bFrequencyLog.setSubmitOrder(apiNumJson.getLong("number"));
                                    //超限频次
                                    Long exceedNum = Long.valueOf(redisTemplate.opsForValue().get(key+"_submitOrder").toString());
                                    Long exceedNumCont = Long.valueOf(
                                            new BigDecimal(bFrequencyLog.getSubmitOrder().toString()).subtract(new BigDecimal(exceedNum.toString())).toString());
                                    bFrequencyLog.setSubmitOrderExceed(exceedNumCont>0?exceedNumCont:0);
                                    break;
                                }
                                case "hotel.order.statechange":
                                {
                                    //监测订单状态变化频次
                                    //访问频次
                                    bFrequencyLog.setOrderstatusChange(apiNumJson.getLong("number"));
                                    //超限频次
                                    Long exceedNum = Long.valueOf(redisTemplate.opsForValue().get(key+"_orderstatusChange").toString());
                                    Long exceedNumCont = Long.valueOf(
                                            new BigDecimal(bFrequencyLog.getOrderstatusChange().toString()).subtract(new BigDecimal(exceedNum.toString())).toString());
                                    bFrequencyLog.setOrderstatusChangeExceed(exceedNumCont>0?exceedNumCont:0);
                                    break;
                                }
                                case "hotel.order.detail":
                                {
                                    //获取订单详情频次
                                    //访问频次
                                    bFrequencyLog.setOrderDetail(apiNumJson.getLong("number"));
                                    //超限频次
                                    Long exceedNum = Long.valueOf(redisTemplate.opsForValue().get(key+"_orderDetail").toString());
                                    Long exceedNumCont = Long.valueOf(
                                            new BigDecimal(bFrequencyLog.getOrderDetail().toString()).subtract(new BigDecimal(exceedNum.toString())).toString());
                                    bFrequencyLog.setOrderDetailExceed(exceedNumCont>0?exceedNumCont:0);
                                    break;
                                }
                                case "hotel.order.cancel":
                                {
                                    //取消订单频次
                                    //访问频次
                                    bFrequencyLog.setCancelOrder(apiNumJson.getLong("number"));
                                    //超限频次
                                    Long exceedNum = Long.valueOf(redisTemplate.opsForValue().get(key+"_cancelOrder").toString());
                                    Long exceedNumCont = Long.valueOf(
                                            new BigDecimal(bFrequencyLog.getCancelOrder().toString()).subtract(new BigDecimal(exceedNum.toString())).toString());
                                    bFrequencyLog.setCancelOrderExceed(exceedNumCont>0?exceedNumCont:0);
                                    break;
                                }
                        }
                    }
                    bFrequencyLogList.add(bFrequencyLog);
                }
            }
        }

        if ( bFrequencyLogList.size() > 0 )
        {
            ibFrequencyLogService.saveOrUpdateBatch(bFrequencyLogList);
        }
    }

    @SuppressWarnings("unchecked")
     public static Cursor<String> scan(RedisTemplate redisTemplate, String pattern, int limit) {
         ScanOptions options = ScanOptions.scanOptions().match(pattern).count(limit).build();
         RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
         return (Cursor) redisTemplate.executeWithStickyConnection(new RedisCallback() {
             @Override
             public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                 return new ConvertingCursor<>(redisConnection.scan(options), redisSerializer::deserialize);
             }
         });
     }
}
