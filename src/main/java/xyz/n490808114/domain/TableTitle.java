package xyz.n490808114.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class TableTitle {

    private static final Map<String,String> noticeTitle = new LinkedHashMap<>();
    static {
        noticeTitle.put("id","���");
        noticeTitle.put("title","����");
        noticeTitle.put("content","��������");
        noticeTitle.put("createDate","��������");
        noticeTitle.put("user","������");
    }
    public static Map<String, String> NoticeTile(){
        return noticeTitle;

    }
}
