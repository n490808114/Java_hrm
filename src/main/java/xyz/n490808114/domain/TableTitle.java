package xyz.n490808114.domain;


import java.util.HashMap;
import java.util.Map;

public class TableTitle {

    private static final Map<String,String> noticeTitle = new HashMap<>();
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
