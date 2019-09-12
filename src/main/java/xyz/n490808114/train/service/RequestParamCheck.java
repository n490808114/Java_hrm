package xyz.n490808114.train.service;

import java.util.Map;


public class RequestParamCheck {
    public static void check(Map<String,String> param){
        if(param.get("pageNo") == null || param.get("pageNo").equals("")){param.put("pageNo", "1");}
        if(param.get("pageSize") == null || param.get("pageSize").equals("")){param.put("pageSize","20");}
        if(param.get("title") != null && "".equals(param.get("title"))){param.remove("title");}
    }
}