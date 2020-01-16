package com.fntx.admin.utils;

import com.fntx.admin.service.IHotelBasepriceService;
import com.fntx.common.domain.BHotelBaseprice;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: IHotelBasepriceUtil
 * @Author: 王俊文
 * @Date: 19-8-12 下午4:59
 * @Description: 多线程处理起价条件获取
 */
@Component
public class HotelBasepriceUtil implements Runnable
{
    private Thread t;
    private IHotelBasepriceService basepriceService;
    private List<BHotelBaseprice> bHotelBasepriceList;

    public HotelBasepriceUtil()
    {

    }

    public HotelBasepriceUtil(IHotelBasepriceService basepriceService, List<BHotelBaseprice> bHotelBasepriceList)
    {
        this.basepriceService = basepriceService;
        this.bHotelBasepriceList = bHotelBasepriceList;
    }

    @Override
    public void run()
    {
        basepriceService.hotelBasepriceScreening(bHotelBasepriceList);
    }

    public void start () {
      if (t == null) {
         t = new Thread (this);
         t.start ();
      }
   }
}
