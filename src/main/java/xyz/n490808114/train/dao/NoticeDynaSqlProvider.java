package xyz.n490808114.train.dao;

import java.util.*;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.ibatis.jdbc.SQL;

public class NoticeDynaSqlProvider{
    private static Log log = LogFactory.getLog(NoticeDynaSqlProvider.class);

    public String  getNoticeListByParam(Map<String,Object> param){
        int pageSize = (Integer) param.get("pageSize");
        Object o =  param.get("title");
        final String[] list = (o != null)?((String) o).split(" "):null;
        String r = new SQL(){
            {
                SELECT("id,title,create_date,user_id");
                FROM("notice_inf");
                WHERE("id>=("+ getSelectId(param)+")");
                if( list != null ){
                    for(String s : list){
                        if(!s.equals("")){
                            AND();
                            WHERE("title LIKE '%"+ s +"%'");
                        }
                    }
                }
            }
        }.toString()+ " limit " + pageSize;
        log.info(r);
        return  r;
    }
    private String getSelectId(Map<String,Object> param){
        int pageSize = (Integer) param.get("pageSize");
        int start =pageSize * (((Integer) param.get("pageNo")) - 1);
        Object o =  param.get("title");
        final String[] list = (o != null)?((String) o).split(" "):null;
        return new SQL(){
            {
                SELECT("id");
                FROM("notice_inf");
                if( list != null ){
                    for(String s : list){
                        if(!s.equals("")){
                            AND();
                            WHERE("title LIKE '%"+ s +"%'");
                        }
                    }
                }
            }
        }.toString()+" limit "+start+",1";
    }
    public String getCount(Map<String,String> param){
        String str =  param.get("title");
        String r = new SQL(){
            {
                SELECT("COUNT(id)");
                FROM("notice_inf");
                if(str != null  && !str.equals("")){
                    for(String s : str.split(" ")){
                        if(!s.equals("")){
                            AND();
                            WHERE("title LIKE '%"+ s +"%'");
                        }
                    }
                }
            }
        }.toString();
        log.info(r);
        return r;
    }
}