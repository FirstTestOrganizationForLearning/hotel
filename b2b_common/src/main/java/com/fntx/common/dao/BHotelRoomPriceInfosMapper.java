package com.fntx.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fntx.common.domain.BHotelRoomPriceInfos;
import com.fntx.common.domain.dto.RoomPriceItemsDto;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 直连入离报价-物理房型表 Mapper 接口
 * </p>
 *
 * @author kang
 * @since 2019-07-22
 */
public interface BHotelRoomPriceInfosMapper extends BaseMapper<BHotelRoomPriceInfos>
{
    /**
     * @Description: 指定酒店下可用房型详情封装
     * @Author: 王俊文
     * @Date: 19-7-23 下午8:59
     * @Param: [paramMap]
     * @returns: java.util.List<RoomPriceItemsDto>
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-23 下午8:59          1.0
     */
    List<RoomPriceItemsDto> getRoomPriceItemsList(Map<String, Object> paramMap);

    /**
     * @Description: 指定酒店下可用房型id列表
     * @Author: 王俊文
     * @Date: 19-7-23 下午8:59
     * @Param: [paramMap]
     * @returns: java.util.List<java.lang.Integer>
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-23 下午8:59          1.0     指定酒店下可用房型id列表
     */
    List<Integer> getRoomIdList(Map<String, Object> paramMap);

    /**
     * @Description: 判断指定酒店下的数据是否为最新的.  即起始数据是否为当前月.
     * @Author: 王俊文
     * @Date: 19-8-14 下午5:41
     * @Param: [paramMap]
     * @returns: java.lang.Integer
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-14 下午5:41          1.0     判断指定酒店下的数据是否为最新的.  即起始数据是否为当前月.
     */
    Integer getHotelRoomNumber(Map<String, Object> paramMap);
}
