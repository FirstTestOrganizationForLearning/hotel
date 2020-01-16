package com.fntx.admin.service;



import com.fntx.common.domain.BFrequencyConfig;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: APIConfigServiceImpl
 * @Author: 王俊文
 * @Date: 2019/8/30 下午2:26
 * @Description: api配置相关功能服务层
 */
public interface IAPIConfigService
{
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
    void initApiConfig();

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/10/11 17:34
     * @Description: 简单分页
     */
    Map getList(Page<BFrequencyConfig> page);

     /**
      * @Copyright (C), 2019, 弗恩天下
      * @Author: 渠猛
      * @Date: 2019/10/11 17:34
      * @Description: 添加
      */
    int add(BFrequencyConfig bFrequencyConfig);

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/10/12 13:31
     * @Description: 删除
     * @param compid
     * @param sid
     */
    int del(String compid, String sid);
    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/10/12 14:15
     * @Description: 根据compid,sid获取详情
     */
    BFrequencyConfig getByCompId(String compid, String sid);
    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/10/12 16:49
     * @Description: 编辑
     */
    int edit(BFrequencyConfig bFrequencyConfig);
}
