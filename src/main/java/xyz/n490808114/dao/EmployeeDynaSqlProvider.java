package xyz.n490808114.dao;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Qualifier;

import xyz.n490808114.domain.Employee;
import xyz.n490808114.service.HrmService;

import java.text.SimpleDateFormat;
import java.util.*;

public class EmployeeDynaSqlProvider {
    @Qualifier("hrmServiceImpl")
    HrmService hrmService;

    public String insertByParam(Map<String,String> param){
        Map<String,String> sqlMapping = Employee.getSqlMapping();
        /*
        String head = "INSERT INTO employee_inf";
        Stack<String> columns = new Stack<>();
        Stack<String> values = new Stack<>();
        for(String s : param.keySet()){
            if(!"id".equals(s) && !param.get(s).equals("")){
                columns.push(sqlMapping.get(s));
                values.push(param.get(s));
            }
        }
        StringBuilder columnsSb = new StringBuilder();
        StringBuilder valuesSb = new StringBuilder();
        while(true){
            columnsSb.append(columns.pop());
            valuesSb.append(values.pop());
            if(!columns.empty()){
                columnsSb.append(",");
                valuesSb.append(",");
            }else{break;}
        }
        System.out.println(head + "  ( " + columnsSb.toString() +" ) VALUES ( "+valuesSb.toString() + " );");
        return head + "  ( " + columnsSb.toString() +" ) VALUES ( "+valuesSb.toString() + " );";
        */
        
        return new SQL(){
            {
                INSERT_INTO("employee_inf");
                for(String s : param.keySet()){
                    System.out.println("K:"+sqlMapping.get(s)+" Value:"+param.get(s)+"  "+"id".equals(s)+"  "+param.get(s).equals(""));

                    if(!"id".equals(s) && !param.get(s).equals("")){
                        VALUES(sqlMapping.get(s), param.get(s));
                        System.out.println("K:"+sqlMapping.get(s)+" Value:"+param.get(s));
                    }else{
                        System.out.println("XXXX K:"+sqlMapping.get(s)+" Value:"+param.get(s));
                    }
                }
                System.out.println("in");
                VALUES("create_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                System.out.println("out");
            }
        }.toString();
        
    }

    public String selectWithParam(Map<String,Object> param){
        return new SQL(){
            {
                SELECT("*");
                FROM("employee_inf");
                if(param.get("id") != null){
                    WHERE("id = #{id}");
                }
                if(param.get("name")!=null){
                    WHERE("name = #{name}");
                }
                if (param.get("cardId") != null){
                    WHERE("card_id = #{cardId}");
                }
                if (param.get("address") != null){
                    WHERE("address = #{address}");
                }
                if (param.get("postCode") != null){
                    WHERE("post_code = #{postCode} ");
                }
                if (param.get("tel") != null){
                    WHERE("tel = #{tel}");
                }
                if (param.get("phone") != null){
                    WHERE("phone = #{phone}");
                }
                if (param.get("qqNum") != null){
                    WHERE("qq_num = #{qqNum}");
                }
                if (param.get("email") != null){
                    WHERE("email = #{email}");
                }
                if (param.get("sex") != null){
                    WHERE("sex = #{sex}");
                }
                if (param.get("party") != null){
                    WHERE("party = #{party}");
                }
                if (param.get("birthday") != null){
                    WHERE("birthday = #{birthday}");
                }
                if (param.get("race") != null){
                    WHERE("race = #{race}");
                }
                if (param.get("education") != null){
                    WHERE("education = #{education}");
                }
                if (param.get("speciality") != null){
                    WHERE("speciality = #{speciality}");
                }
                if (param.get("hobby") != null){
                    WHERE("hobby = #{hobby}");
                }
                if (param.get("remark") != null){
                    WHERE("remark = #{remark}");
                }
                if (param.get("createDate") != null){
                    WHERE("create_date = #{createDate}");
                }
            }
        }.toString();
    }

    public String modify(Map<String,String> param){
        int deptId = hrmService.findDeptByName(param.get("dept")).getId();
        int jobId = hrmService.findJobByName(param.get("job")).getId();
        return new SQL(){
            {
                UPDATE("employee_inf");
                if(param.get("dept") != null){
                    SET("dept_id = "+  deptId);
                }
                if(param.get("job") != null){
                    SET("job_id = "+ jobId);
                }
                if(param.get("name") !=null){
                    SET("name = #{name}");
                }
                if(param.get("cardId") !=null){
                    SET("card_id = #{cardId}");
                }
                if(param.get("address") !=null){
                    SET("address = #{address}");
                }
                if(param.get("postCode") !=null){
                    SET("post_code = #{postCode}");
                }
                if(param.get("tel") !=null){
                    SET("tel = #{tel}");
                }
                if(param.get("phone") !=null){
                    SET("phone = #{phone}");
                }
                if(param.get("qqNum") !=null){
                    SET("qq_num = #{qqNum}");
                }
                if(param.get("email") !=null){
                    SET("email = #{email}");
                }
                if(param.get("sex") !=null){
                    SET("sex = #{sex}");
                }
                if(param.get("party") !=null){
                    SET("party = #{party}");
                }
                if(param.get("birthday") !=null){
                    SET("birthday = #{birthday}");
                }
                if(param.get("race") !=null){
                    SET("race = #{race}");
                }
                if(param.get("education") !=null){
                    SET("education = #{education}");
                }
                if(param.get("speciality") !=null){
                    SET("speciality = #{speciality}");
                }
                if(param.get("hobby") !=null){
                    SET("hobby = #{hobby}");
                }
                if(param.get("remark") !=null){
                    SET("remark = #{remark}");
                }
                if(param.get("createDate") !=null){
                    SET("create_date = #{createDate}");
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
    public String getEmployeeListByPageNoAndPageSize(Map<String,Object> param){
        int pageSize = (Integer) param.get("pageSize");
        int start =pageSize * ((Integer) param.get("pageNo") - 1);
        
        return new SQL(){
            {
                SELECT("id,name,dept_id,job_id,phone");
                FROM("employee_inf");
                WHERE("id>=(SELECT id FROM notice_inf LIMIT  "+start +",1)");
                
            }
        }.toString() + " limit " + pageSize;
    }
}
