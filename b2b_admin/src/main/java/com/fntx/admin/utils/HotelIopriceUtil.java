package com.fntx.admin.utils;

import com.fntx.admin.service.IHotelIopriceService;
import com.fntx.common.domain.BHotelRoomPriceInfos;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: IHotelBasepriceUtil
 * @Author: 王俊文
 * @Date: 19-8-12 下午4:59
 * @Description: 多线程处理直连报价条件获取
 */
@Component
public class HotelIopriceUtil implements Runnable
{
    private Thread t;
    private IHotelIopriceService iHotelIopriceService;
    private List<BHotelRoomPriceInfos> bHotelRoomPriceInfosList;

    public HotelIopriceUtil()
    {

    }

    public HotelIopriceUtil(IHotelIopriceService iHotelIopriceService, List<BHotelRoomPriceInfos> bHotelRoomPriceInfosList)
    {
        this.iHotelIopriceService = iHotelIopriceService;
        this.bHotelRoomPriceInfosList = bHotelRoomPriceInfosList;
    }

    @Override
    public void run()
    {
        iHotelIopriceService.hotelIopriceScreening(bHotelRoomPriceInfosList);
    }

    public void start () {
      if (t == null) {
         t = new Thread (this);
         t.start ();
      }
   }
}
