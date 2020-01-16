package com.fntx.common.dao;

import com.fntx.common.domain.BStaticChangeInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fntx.common.domain.dto.HotelIncrDataDto;
import com.fntx.common.domain.dto.HotelIncrDataWarehousingDto;
import feign.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 静态信息增量更新表 Mapper 接口
 * </p>
 *
 * @author wang
 * @since 2019-08-06
 */
public interface BStaticChangeInfoMapper extends BaseMapper<BStaticChangeInfo> {

    /**
     * @Description: 批量插入静态增量信息
     * @Author: 王俊文
     * @Date: 19-8-6 下午4:11
     * @Param: [hotelIncrDataWarehousingDtoList]
     * @returns: java.lang.Integer
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-6 下午4:11          1.0          批量插入静态增量信息
     */
    Integer insertBstaticChangeList(List<HotelIncrDataWarehousingDto> hotelIncrDataWarehousingDtoList);

    /**
     * @Description: 获取增量静态数据相关分页id
     * @Author: 王俊文
     * @Date: 19-8-7 下午1:48
     * @Param: [list]
     * @returns: java.util.List<com.fntx.common.domain.dto.HotelIncrDataDto>
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-7 下午1:48          1.0          获取增量静态数据相关分页id
     */
    List<HotelIncrDataDto> selectIncrDataList(@Param("value=list") List<HotelIncrDataWarehousingDto> list);

    /**
     * @Description: 获取指定条件下分页数据
     * @Author: 王俊文
     * @Date: 19-8-7 下午1:49
     * @Param: [paramMap]
     * @returns: java.util.List<com.fntx.common.domain.dto.HotelIncrDataWarehousingDto>
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-7 下午1:49          1.0
     */
    List<HotelIncrDataWarehousingDto> selectIncrDataListLimit(Map<String, Object> paramMap);
}
