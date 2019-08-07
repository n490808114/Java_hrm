package xyz.n490808114.train.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.n490808114.train.util.TableTitle;
import xyz.n490808114.train.service.HrmService;

@Controller
public class DeptController{
    @Autowired
    @Qualifier("hrmServiceImpl")
    HrmService hrmService;

    @RequestMapping(value = "/dept",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> getList(@RequestParam("pageNo")String pageNo,@RequestParam("pageSize")String pageSize){
        Map<String,Object> json = new HashMap<>();
        json.put("count", hrmService.getDeptCount());
        System.out.println(hrmService.getDeptCount());
        json.put("pageNo",pageNo);
        json.put("pageSize",pageSize);
        json.put("title",TableTitle.deptListTitle());
        System.out.println(TableTitle.deptListTitle());
        json.put("data",hrmService.getDeptList(pageNo, pageSize));
        System.out.println(hrmService.getDeptList(pageNo, pageSize));
        System.out.println(json);
        return json;
    }
}