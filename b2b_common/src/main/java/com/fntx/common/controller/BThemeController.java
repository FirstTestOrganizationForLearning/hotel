package com.fntx.common.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fntx.common.domain.BTheme;
import com.fntx.common.service.IBThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 酒店主题信息表 前端控制器
 * </p>
 *
 * @author kang
 * @since 2019-07-12
 */
@RestController
@RequestMapping("/bTheme")
public class BThemeController {
    @Autowired
    IBThemeService ibThemeService;
    @RequestMapping("getList")
    public String getList(){
        QueryWrapper<BTheme> queryWrapper = new QueryWrapper<BTheme>();
        queryWrapper.like("THEME_NAME","酒店");
        List<BTheme> list = ibThemeService.list(queryWrapper);
        return list.toString();
    }
}
