package com.fntx.admin.controller;


import com.fntx.admin.service.IAPIConfigService;
import com.fntx.common.domain.BFrequencyConfig;
import com.fntx.common.domain.BasePage;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: APIConfigController
 * @Author: 王俊文
 * @Date: 2019/8/30 下午2:14
 * @Description: api配置相关功能接口
 */
@Controller
@RequestMapping("apiConfig")
public class APIConfigController
{
    private static final Logger logger = LoggerFactory.getLogger(APIConfigController.class);

    @Autowired
    private IAPIConfigService iapiConfigService;


    @GetMapping("/")
    public String get(){
        return  "view/apiconfig/list.html";
    }
    @GetMapping("/add")
    public String add(){
        return  "view/apiconfig/add.html";
    }

    @GetMapping("/edit/{compid}/{sid}")
    public String add(@PathVariable String compid,@PathVariable String sid, Model model){
        BFrequencyConfig config=iapiConfigService.getByCompId(compid,sid);
        if (config!=null){
            model.addAttribute("configDetail",config);
            return  "view/apiconfig/edit.html";
        }else {
            //TODO 失败就跳转到失败页面
            model.addAttribute("msg","获取记录失败");
            return "view/apiconfig/erro.html";
        }

    }

    @GetMapping("/list")
    @ResponseBody
    public Object getList(BasePage pageReq){
        logger.info("-----分销商请求频次列表------");
        logger.info("参数:"+pageReq);
        Page<BFrequencyConfig>page=new Page<>(pageReq.getPageNum(),pageReq.getPageSize());
        final Map list = iapiConfigService.getList(page);
        logger.info("返回值:"+list);
        return  list;
    }

    @PostMapping("/save")
    @ResponseBody
    public Object getList(BFrequencyConfig bFrequencyConfig){
        logger.info("-----分销商请求频次配置添加------");
        logger.info("参数:"+bFrequencyConfig);
        Map<String,Object> restult=new HashMap<>(5);
        try {
            int resultFlag=iapiConfigService.add(bFrequencyConfig);
            if (resultFlag==1){
                restult.put("status",0);
                restult.put("msg","添加成功");
            }else {
                restult.put("status",1);
                restult.put("msg","添加失败");
            }
        }catch (Exception e){
            restult.put("status",1);
            restult.put("msg","添加失败");
        }
        logger.info("返回值:"+restult);
        return  restult;
    }

    @PostMapping("/edit")
    @ResponseBody
    public Object edit(BFrequencyConfig bFrequencyConfig){
        logger.info("-----分销商请求频次配置编辑------");
        logger.info("参数:"+bFrequencyConfig);
        Map<String,Object> restult=new HashMap<>(5);
        try {
            int resultFlag=iapiConfigService.edit(bFrequencyConfig);
            if (resultFlag==1){
                restult.put("status",0);
                restult.put("msg","更新成功");
            }else {
                restult.put("status",1);
                restult.put("msg","更新失败");
            }
        }catch (Exception e){
            restult.put("status",1);
            restult.put("msg","更新失败");
        }
        logger.info("返回值:"+restult);
        return  restult;
    }

    @PostMapping("/del/{compid}/{sid}")
    @ResponseBody
    public Object del(@PathVariable String compid,@PathVariable String sid){
        logger.info("-----分销商请求频次配置删除------");
        logger.info("参数 compid:"+compid);
        logger.info("参数 sid:"+sid);
        Map<String,Object> restult=new HashMap<>(5);
        try {
            int resultFlag=iapiConfigService.del(compid,sid);
            if (resultFlag==1){
                restult.put("status",0);
                restult.put("msg","删除成功");
            }else {
                restult.put("status",1);
                restult.put("msg","删除失败");
            }
        }catch (Exception e){
            restult.put("status",1);
            restult.put("msg","删除失败");
        }
        logger.info("返回值:"+restult);
        return  restult;
    }

    @PostConstruct
    @GetMapping("/frequencyConfigInit")
    public void frequencyConfigInit()
    {
        logger.info(
            "\n***************************************" + "\n" +
            "start frequencyConfigInit" + "\n" +
            "分销商请求频次初始化" + "\n" +
            "\n********************************************"
        );
        try
        {
            iapiConfigService.initApiConfig();
        }catch (Exception e)
        {
            e.getMessage();
        }
        logger.info(
            "\n***************************************" + "\n" +
            "end 分销商请求频次初始化" + "\n" +
            "消息队列" + "\n" +
            "\n********************************************"
        );
    }
}
