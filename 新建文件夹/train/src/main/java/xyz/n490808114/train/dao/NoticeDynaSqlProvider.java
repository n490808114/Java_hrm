package xyz.n490808114.train.dao;

import java.util.*;
import org.apache.ibatis.jdbc.SQL;

public class NoticeDynaSqlProvider{

    public String  getNoticeListByPageNoAndPageSize(Map<String,Object> param){
        int pageSize = (Integer) param.get("pageSize");
        int start =pageSize * (((Integer) param.get("pageNo"))- 1);
        
        return new SQL(){
            {
                SELECT("id,title,create_date,user_id");
                FROM("notice_inf");
                WHERE("id>=(SELECT id FROM notice_inf LIMIT  "+start +",1)");
                
            }
        }.toString() + " limit " + pageSize;
    }
}