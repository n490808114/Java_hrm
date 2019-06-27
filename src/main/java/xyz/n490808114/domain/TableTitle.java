package xyz.n490808114.domain;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TableTitle {

    private static final Map<String,String> noticeTitle = new LinkedHashMap<>();
    static {
        noticeTitle.put("id","序号");
        noticeTitle.put("title","标题");
        noticeTitle.put("content","公告内容");
        noticeTitle.put("createDate","最后更改日期");
        noticeTitle.put("user","创建人");
    }
    public static Map<String, String> NoticeTitle(){
        HashMap<String,String> r = new HashMap<>();
        r.putAll(noticeTitle);
        return r;

    }
}
