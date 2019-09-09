package xyz.n490808114.train.dao;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class DocumentDynaSqlProvider {

    public String getListByParam(Map<String,String> param){
        int pageSize = Integer.parseInt(param.get("pageSize"));
        int id = param.get("id") == null?-1:Integer.parseInt(param.get("id"));
        String[] titles = (param.get("title") == null || param.get("title").equals(""))?null:param.get("title").split(" ");
        return new SQL(){
            {
                SELECT("id,title,create_date,user_id");
                FROM("document_inf");
                WHERE("id >= (" + getSelectId(param)+ ")");
                if(id != -1){
                    WHERE("id = "+id);
                }
                if(titles != null){
                    for(String title:titles){
                        WHERE("title LIKE %"+title+"%");
                    }
                }
            }
        }.toString() + " limit " + pageSize;
    }
    private String getSelectId(Map<String, String> param){
        int pageSize = Integer.parseInt(param.get("pageSize")) ;
        int start =pageSize * (Integer.parseInt(param.get("pageNo")) - 1);
        int id = param.get("id") == null?-1:Integer.parseInt(param.get("id"));
        String[] titles = (param.get("title") == null || param.get("title").equals(""))?null:param.get("title").split(" ");
        return new SQL(){
            {
                SELECT("id");
                FROM("document_inf");
                if(id != -1){
                    WHERE("id = "+id);
                }
                if(titles != null){
                    for(String title:titles){
                        WHERE("title LIKE %"+title+"%");
                    }
                }
            }
        }.toString() + " limit " + start + ",1";
    }
    public String getCountByParam(Map<String,String> param){
        int id = param.get("id") == null?-1:Integer.parseInt(param.get("id"));
        String[] titles = (param.get("title") == null || param.get("title").equals(""))?null:param.get("title").split(" ");
        return new SQL(){
            {
                SELECT("COUNT(id)");
                FROM("document_inf");
                if(id != -1){
                    WHERE("id = "+id);
                }
                if(titles != null){
                    for(String title:titles){
                        WHERE("title LIKE %"+title+"%");
                    }
                }
            }
        }.toString();
    }
}
