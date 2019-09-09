package xyz.n490808114.train.service;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestParamCheck {
    private static final Log log = LogFactory.getLog(RequestParamCheck.class);
    public static void check(Map<String,String> param){
        if(param.get("pageNo") == null || param.get("pageNo").equals("")){param.put("pageNo", "1");}
        if(param.get("pageSize") == null || param.get("pageSize").equals("")){param.put("pageSize","20");}
        if(param.get("title") != null && "".equals(param.get("title"))){param.remove("title");}
        log.info(param);
    }
}