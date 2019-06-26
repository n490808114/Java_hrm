package xyz.n490808114.dao;

import java.util.*;
import org.apache.ibatis.jdbc.SQL;

class NoticeDynaSqlProvider{

    public String  getNoticeListByPageNoAndPageSize(Map<String,Object> param){
        return new SQL(){
            {
                SELECT("*");
                FROM("notice_inf");
                WHERE("id>=(SELECT id FROM notice_inf) LIMIT #{pageSize} OFFSET #{pageNo} * #{pageSize}");
            }
        }.toString();
    }
}