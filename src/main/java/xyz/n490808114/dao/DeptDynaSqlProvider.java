package xyz.n490808114.dao;

import org.apache.ibatis.jdbc.SQL;

public class DeptDynaSqlProvider {
    
    public String getDeptListByPageNoAndPageSize(String pageNo,String pageSize){
        int start =Integer.parseInt(pageSize) * (Integer.parseInt(pageNo) - 1);

        return new SQL(){
            {
                SELECT("id,name,dept_id,job_id,phone");
                FROM("dept_inf");
                WHERE("id>=(SELECT id FROM notice_inf LIMIT  "+start +",1)");
                
            }
        }.toString() + " limit " + pageSize;
    }
}
