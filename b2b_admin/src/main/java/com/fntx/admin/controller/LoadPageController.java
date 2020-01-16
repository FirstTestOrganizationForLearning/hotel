package com.fntx.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: LoadPageController
 * @Author: 王俊文
 * @Date: 2019/10/10 上午11:34
 * @Description: 界面跳转
 */
@Controller
@RequestMapping("loadpage")
public class LoadPageController
{
    private static final Logger logger = LoggerFactory.getLogger(LoadPageController.class);

    /**
     * 测试首页
     * @return
     */
    @GetMapping("/index")
    public ModelAndView ticketFlightChangeInsertInterface()
    {
        ModelAndView modelAndView = new ModelAndView();
        logger.info(
            "\n***************************************" + "\n" +
            "start index" + "\n" +
            "测试首页" + "\n" +
            "\n********************************************"
        );

        modelAndView.setViewName("index");

        logger.info(
            "\n***************************************" + "\n" +
            "end index" + "\n" +
            "测试首页" + "\n" +
            "modelAndView = " + modelAndView + "\n" +
            "\n********************************************"
        );
        return modelAndView;
    }
}
