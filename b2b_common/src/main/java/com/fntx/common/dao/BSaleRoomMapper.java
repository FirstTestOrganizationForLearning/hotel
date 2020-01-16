package com.fntx.common.dao;

import com.fntx.common.domain.BSaleRoom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fntx.common.domain.dto.BSaleRoomDto;
import com.fntx.common.domain.dto.HotelIncrRoomtypeDto;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 售卖房型表 Mapper 接口
 * </p>
 *
 * @author kang
 * @since 2019-07-31
 */
public interface BSaleRoomMapper extends BaseMapper<BSaleRoom>
{

    /**
     * @Description: 批量更新房型上下架信息
     * @Author: 王俊文
     * @Date: 19-8-1 上午10:16
     * @Param: [hotelIncrRoomtypeDtoList]
     * @returns: int
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-1 上午10:16          1.0          批量更新房型上下架信息
     */
    int updateBSaleRoomList(List<HotelIncrRoomtypeDto> hotelIncrRoomtypeDtoList ) ;

    /**
     * @Description: 获取售卖房型,物理房型指定信息列表
     * @Author: 王俊文
     * @Date: 19-8-1 上午10:19
     * @Param: [paramMap]
     * @returns: com.fntx.common.domain.dto.BSaleRoomDto
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-1 上午10:19          1.0          获取售卖房型,物理房型指定信息列表
     */
    BSaleRoomDto selectBSaleRoomType(Map<String, Object> paramMap);

    /**
     * @Description: 获取房型上下架信息
     * @Author: 王俊文
     * @Date: 19-8-1 下午3:01
     * @Param: [paramMap]
     * @returns: java.util.List<com.fntx.common.domain.dto.HotelIncrRoomtypeDto>
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-8-1 下午3:01          1.0          获取房型上下架信息
     */
    List<HotelIncrRoomtypeDto> selectBSaleRoomUpDownLine(Map<String, Object> paramMap);

}
