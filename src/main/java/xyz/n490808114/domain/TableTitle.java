package xyz.n490808114.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class TableTitle {

    private static final Map<String,String> noticeTitle = new LinkedHashMap<>();
    static {
        noticeTitle.put("id","序号");
        noticeTitle.put("title","标题");
        noticeTitle.put("content","公告内容");
        noticeTitle.put("createDate","创建日期");
        noticeTitle.put("user","创建者");
    }
    public static Map<String, String> NoticeTile(){
        return noticeTitle;

    }
}
