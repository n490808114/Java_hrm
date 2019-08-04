package xyz.n490808114.controller;

import com.alibaba.fastjson.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.n490808114.service.HrmService;

public class DeptController{
    @Autowired
    @Qualifier("hrmServiceImpl")
    HrmService hrmService;

    @RequestMapping(value = "/dept",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getList(){
        return JSON.toJSONString(hrmService.findAllDept());
    }
}