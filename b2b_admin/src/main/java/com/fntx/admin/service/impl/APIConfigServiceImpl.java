package com.fntx.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fntx.admin.service.IAPIConfigService;
import com.fntx.common.dao.BFrequencyConfigMapper;
import com.fntx.common.domain.BFrequencyConfig;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: APIConfigServiceImpl
 * @Author: 王俊文
 * @Date: 2019/8/30 下午2:26
 * @Description: api配置相关功能服务实现层
 */
@Service
public class APIConfigServiceImpl implements IAPIConfigService
{

    private static final Logger logger = LoggerFactory.getLogger(APIConfigServiceImpl.class);

    private static final String API_CONFIG_REDIS_KEY = "API_CONFIG_";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private BFrequencyConfigMapper bFrequencyConfigMapper;

    /**
     * @Description: 初始化所有分销商频次配置信息
     * @Author: 王俊文
     * @Date: 2019/8/30 下午2:28
     * @Param: []
     * @returns: void
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     2019/8/30 下午2:28          1.0          初始化所有分销商频次配置信息
     */
    @Override
    //项目启动初始化分销商频次配置信息
    public void initApiConfig()
    {
        //获取所有分销商配置信息
        QueryWrapper queryWrapper = new QueryWrapper();
        List<BFrequencyConfig> bFrequencyConfigList = bFrequencyConfigMapper.selectList(queryWrapper);

        int i = 10;
        while (i>0)
        {
            boolean sign = initBFrequencyConfig(bFrequencyConfigList);
            if ( sign )
            {
                break;
            }
            i--;
        }
    }

    @Override
    public Map getList(Page<BFrequencyConfig> page) {
        Map<String,Object> map = new HashMap<>();
        Page<BFrequencyConfig> pageResult = PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<BFrequencyConfig> bFrequencyConfigs = bFrequencyConfigMapper.selectLists();
        map.put("code",0);
        map.put("msg","");
        map.put("data",pageResult.getResult());
        map.put("count",pageResult.getTotal());
        return  map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(BFrequencyConfig bFrequencyConfig) {
        // 1.添加 配置
        bFrequencyConfig.setCreateTime(new Date());
        bFrequencyConfig.setUpdateTime(new Date());
        final int insert = bFrequencyConfigMapper.insert(bFrequencyConfig);
        if (insert!=1){
            //添加不成功
            return 0;
        }else {
            //2.刷新 redis缓存
            initApiConfig();
        }
        return insert;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int del(String compid, String sid) {
        // 1.删除 配置
        Map<String,Object>delBy=new HashMap<>();
        delBy.put("compid",compid);
        delBy.put("sid",sid);
        final int del = bFrequencyConfigMapper.deleteByMap(delBy);
        if (del!=1){
            //删除不成功
            return 0;
        }else {
            //2.刷新 redis缓存
            initApiConfig();
        }
        return del;
    }

    @Override
    public BFrequencyConfig getByCompId(String compid, String sid) {
        Map<String,Object>selBy=new HashMap<>();
        selBy.put("compid",compid);
        selBy.put("sid",sid);
        final List<BFrequencyConfig> bFrequencyConfigs = bFrequencyConfigMapper.selectByMap(selBy);
        if (!CollectionUtils.isEmpty(bFrequencyConfigs) && bFrequencyConfigs.size()==1){
            return bFrequencyConfigs.get(0);
        }else {
            return null;
        }
    }

    @Override
    public int edit(BFrequencyConfig bFrequencyConfig) {
        bFrequencyConfig.setUpdateTime(new Date());
        UpdateWrapper<BFrequencyConfig>updateWrapper=new UpdateWrapper<>();
        //相当于联合主键
        updateWrapper.eq("compid",bFrequencyConfig.getCompid()).eq("sid",bFrequencyConfig.getSid());
        final int update = bFrequencyConfigMapper.update(bFrequencyConfig,updateWrapper);
        if (update!=1){
            //添加不成功
            return 0;
        }else {
            //2.刷新 redis缓存
            initApiConfig();
        }
        return update;
    }

    /**
     * 缓存频次相关配置信息
     * @param bFrequencyConfigList
     * @return
     */
    private boolean initBFrequencyConfig(List<BFrequencyConfig> bFrequencyConfigList)
    {
        try
        {
            for ( BFrequencyConfig bFrequencyConfig : bFrequencyConfigList )
            {
                String key = API_CONFIG_REDIS_KEY + bFrequencyConfig.getCompid() + "_" + bFrequencyConfig.getSid();
                //缓存所有分销商频次配置信息
                //频次限制模式
                redisTemplate.opsForValue().set(key + "_isExceed", bFrequencyConfig.getIsExceed().toString());
                //起价频次
                redisTemplate.opsForValue().set(key + "_basePrice", bFrequencyConfig.getBasePrice().toString());
                //直连报价频次
                redisTemplate.opsForValue().set(key + "_directPrice", bFrequencyConfig.getDirectPrice().toString());
                //房型是否可预定频次
                redisTemplate.opsForValue().set(key + "_isBookroom", bFrequencyConfig.getIsBookroom().toString());
                //创建订单频次
                redisTemplate.opsForValue().set(key + "_createOrder", bFrequencyConfig.getCreateOrder().toString());
                //提交订单频次
                redisTemplate.opsForValue().set(key + "_submitOrder", bFrequencyConfig.getSubmitOrder().toString());
//                //获取支付确认结果频次
//                redisTemplate.opsForValue().set(key + "_payResult", bFrequencyConfig.getPayResult());
                //监测订单状态变化频次
                redisTemplate.opsForValue().set(key + "_orderstatusChange", bFrequencyConfig.getOrderstatusChange().toString());
                //获取订单详情频次
                redisTemplate.opsForValue().set(key + "_orderDetail", bFrequencyConfig.getOrderDetail().toString());
                //取消订单频次
                redisTemplate.opsForValue().set(key + "_cancelOrder", bFrequencyConfig.getCancelOrder().toString());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
