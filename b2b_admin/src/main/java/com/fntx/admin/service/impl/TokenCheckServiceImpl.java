package com.fntx.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fntx.admin.service.ITokenCheckService;
import com.fntx.common.dao.BHotelListMapper;
import com.fntx.common.domain.BHotelList;
import com.fntx.common.utils.TokenCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: TokenCheckServiceImpl
 * @Author: 王俊文
 * @Date: 19-7-11 下午12:07
 * @Description: 分销商token核验
 */
@Service
public class TokenCheckServiceImpl implements ITokenCheckService
{
    @Autowired
    private BHotelListMapper bHotelListMapper;

    /**
     * @Description: 校验分销商token
     * @Author: 王俊文
     * @Date: 19-7-11 下午2:09
     * @Param: [redisToken, accessToken]
     * @returns: boolean
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-11 下午2:09          1.0          校验分销商token是否正确并校验本次操作对应酒店类型是否正确
     */
    @Override
    public String DistributorTokenCheck(String redisToken, String accessToken, String hotelId)
    {
        //校验token
        String accessTokenModuleId = TokenCheck.distributorCheck(redisToken, accessToken);
        if ( accessTokenModuleId == null || "".equals(accessTokenModuleId) )
        {
            return "-1";
        }
        if ( hotelId != null && !"".equals(hotelId) )
        {
            //校验酒店id类型
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("HOTEL_ID", hotelId);
            List<BHotelList> bHotelListList = bHotelListMapper.selectList(queryWrapper);

            if ( bHotelListList == null || bHotelListList.size() != 1 )
            {
                return "-1";
            }
            if ( bHotelListList.get(0).getIsIntel().compareTo(accessTokenModuleId) == 0 )
            {
                return accessTokenModuleId;
            }else
            {
                return "-1";
            }
        }
        return accessTokenModuleId;
    }
}
