package com.fntx.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fntx.common.domain.BHotelBaseprice;
import com.fntx.common.domain.dto.HotelDataLists;
import com.fntx.common.domain.dto.NumEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 酒店入离起价 Mapper 接口
 * </p>
 *
 * @author wang
 * @since 2019-07-18
 */
public interface BHotelBasepriceMapper extends BaseMapper<BHotelBaseprice>
{
    /**
     * @Description: 根据条件获取起价信息
     * @Author: 王俊文
     * @Date: 19-8-9 下午5:50
     * @Param: [paramMap]
     * @returns: java.util.List<com.fntx.common.domain.dto.HotelDataLists>
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-9 下午5:50          1.0          根据条件获取起价信息
     */
    List<HotelDataLists> getBHotelBasepriceList(Map<String, Object> paramMap);

    /**
     * @Description: 根据指定条件获取其中相同的城市信息
     * @Author: 王俊文
     * @Date: 19-8-9 下午5:50
     * @Param: [paramMap]
     * @returns: java.util.List<com.fntx.common.domain.dto.NumEntity>
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-9 下午5:50          1.0          根据指定条件获取其中相同的城市信息
     */
    List<NumEntity> getBHotelCityList(Map<String, Object> paramMap);

    /**
     * @Description: 获取指定条件下的所有hotelid的String串
     * @Author: 王俊文
     * @Date: 19-8-9 下午5:50
     * @Param: [paramMap]
     * @returns: java.lang.String
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-9 下午5:50          1.0          获取指定条件下的所有hotelid的String串
     */
    String getBHotelBasepriceHotelStr(Map<String, Object> paramMap);
}
