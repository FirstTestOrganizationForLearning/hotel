package com.fntx.common.dao;

import com.fntx.common.domain.BFrequencyConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 分销商接口频次配置表 Mapper 接口
 * </p>
 *
 * @author 王俊文
 * @since 2019-08-30
 */
public interface BFrequencyConfigMapper extends BaseMapper<BFrequencyConfig>
{
    List<BFrequencyConfig> selectLists();

}
