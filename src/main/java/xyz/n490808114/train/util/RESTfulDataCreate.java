package xyz.n490808114.train.util;

import java.util.HashMap;
import java.util.Map;

public class RESTfulDataCreate {
    public static Map<String,Object> create(int code,String message,Object data){
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        map.put("message",message);
        map.put("data",data);
        return map;
    }
}
