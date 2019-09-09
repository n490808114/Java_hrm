package xyz.n490808114.train.dao;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class JobDynaSqlProvider {
    public String getListByParam(Map<String,String> param){
        int pageSize = Integer.parseInt(param.get("pageSize"));
        String name = param.get("name");

        return new SQL(){
            {
                SELECT("id,name,remark");
                FROM("job_inf");
                WHERE("id>=("+getSelectId(param)+")");
                if(name !=null && "".equals(name)){
                    WHERE("name  LIKE '%" + name +"%'");
                }
            }
        }.toString() + " limit " + pageSize;
    }
    public String getSelectId(Map<String,String> param){
        int pageNo = Integer.parseInt(param.get("pageNo"));
        int pageSize = Integer.parseInt(param.get("pageSize"));
        String name = param.get("name");
        int start =pageSize * (pageNo - 1);
        return new SQL(){
            {
                SELECT("id");
                FROM("job_inf");
                if(name !=null && "".equals(name)){
                    WHERE("name  LIKE '%" + name +"%'");
                }
            }
        }.toString()+" limit "+start+",1";
    }
    public String getCountByParam(Map<String,String> param){
        String name = param.get("name");
        return new SQL(){
            {
                SELECT("COUNT(id)");
                FROM("job_inf");
                if(name !=null && "".equals(name)){
                    WHERE("name  LIKE '%" + name +"%'");
                }
            }
        }.toString();
    }
}