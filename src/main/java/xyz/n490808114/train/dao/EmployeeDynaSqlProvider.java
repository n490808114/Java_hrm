package xyz.n490808114.train.dao;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Qualifier;

import xyz.n490808114.train.domain.Employee;
import xyz.n490808114.train.service.HrmService;

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
                    if(!param.get(s).equals("")){
                        switch (s){
                            case "id":break;
                            case "name":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "dept":VALUES(sqlMapping.get(s), param.get(s));;break;
                            case "job":VALUES(sqlMapping.get(s), param.get(s));;break;
                            case "cardId":VALUES(sqlMapping.get(s), param.get(s));;break;
                            case "address":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "postCode":VALUES(sqlMapping.get(s), param.get(s));;break;
                            case "tel":VALUES(sqlMapping.get(s), param.get(s));;break;
                            case "phone":VALUES(sqlMapping.get(s), param.get(s));;break;
                            case "qqNum":VALUES(sqlMapping.get(s), param.get(s));;break;
                            case "email":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "sex":VALUES(sqlMapping.get(s), param.get(s));;break;
                            case "party":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "birthday":VALUES(sqlMapping.get(s),  "'"+param.get(s)+"'");;break;
                            case "race":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "education":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "speciality":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "hobby":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "remark":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                        }
                    }
                }
                VALUES("create_date","'" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "'");
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
        if(param.get("id").equals("")){return "";}
        Map<String,String> sqlMapping = Employee.getSqlMapping();
        return new SQL(){
            {
                UPDATE("employee_inf");
                for(String s : param.keySet()){
                    if(!param.get(s).equals("")){
                        switch (s){
                            case "name":SET(sqlMapping.get(s) + " = " + " '"+param.get(s)+"'");;break;
                            case "dept":SET(sqlMapping.get(s) + " = " + param.get(s));;break;
                            case "job":SET(sqlMapping.get(s) + " = " + param.get(s));;break;
                            case "cardId":SET(sqlMapping.get(s) + " = " + param.get(s));;break;
                            case "address":SET(sqlMapping.get(s) + " = " + "'"+param.get(s)+"'");;break;
                            case "postCode":SET(sqlMapping.get(s) + " = " + param.get(s));;break;
                            case "tel":SET(sqlMapping.get(s) + " = " + param.get(s));;break;
                            case "phone":SET(sqlMapping.get(s) + " = " + param.get(s));;break;
                            case "qqNum":SET(sqlMapping.get(s) + " = " + param.get(s));;break;
                            case "email":SET(sqlMapping.get(s) + " = " + "'"+param.get(s)+"'");;break;
                            case "sex":SET(sqlMapping.get(s) + " = " + param.get(s));;break;
                            case "party":SET(sqlMapping.get(s) + " = " + "'"+param.get(s)+"'");;break;
                            case "birthday":SET(sqlMapping.get(s) + " = " + "'"+param.get(s)+"'");;break;
                            case "race":SET(sqlMapping.get(s) + " = " + "'"+param.get(s)+"'");;break;
                            case "education":SET(sqlMapping.get(s) + " = " + "'"+param.get(s)+"'");;break;
                            case "speciality":SET(sqlMapping.get(s) + " = " + "'"+param.get(s)+"'");;break;
                            case "hobby":SET(sqlMapping.get(s) + " = " + "'"+param.get(s)+"'");;break;
                            case "remark":SET(sqlMapping.get(s) + " = " + "'"+param.get(s)+"'");;break;
                        }
                    }
                }
                WHERE("id = " + param.get("id"));
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
                WHERE("id>=(SELECT id FROM employee_inf LIMIT  "+start +",1)");
                
            }
        }.toString() + " limit " + pageSize;
    }
}
