package xyz.n490808114.train.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Qualifier;

import xyz.n490808114.train.domain.Employee;
import xyz.n490808114.train.service.HrmService;
import xyz.n490808114.train.util.TableTitle;

import java.text.SimpleDateFormat;
import java.util.*;

public class EmployeeDynaSqlProvider {
    @Qualifier("hrmServiceImpl")
    HrmService hrmService;

    private static Log log = LogFactory.getLog(EmployeeDynaSqlProvider.class);

    public String insert(Employee employee){
        return new SQL(){
            {
                INSERT_INTO("employee_inf");
                VALUES("dept_id", "" + employee.getDept().getId());
                VALUES("job_id", "" + employee.getJob().getId());
                VALUES("name", "'" + employee.getName() + "'");
                if(!employee.getCardId().equals("")){VALUES("card_id", "'" + employee.getCardId() + "'");}
                if(!employee.getAddress().equals("")){VALUES("address", "'" + employee.getAddress() + "'");}
                if(!employee.getPostCode().equals("")){VALUES("post_code", "'" + employee.getPostCode() + "'");}
                if(!employee.getTel().equals("")){VALUES("tel", "'" + employee.getTel() + "'");}
                VALUES("phone", employee.getPhone());
                if(!employee.getQqNum().equals("")){VALUES("qq_num", "'" + employee.getQqNum() + "'");}
                if(!employee.getEmail().equals("")){VALUES( "email", "'" + employee.getEmail() + "'");}
                VALUES("sex", "" + employee.getSex());
                if(!employee.getParty().equals("")){VALUES("party", "'" + employee.getParty() + "'");}
                if(employee.getBirthday() != null){VALUES("birthday", "'" + new SimpleDateFormat("yyyy-MM-dd").format(employee.getBirthday()) + "'");}
                if(!employee.getRace().equals("")){VALUES("race", "'" + employee.getRace() + "'");}
                if(!employee.getEducation().equals("")){VALUES("education", "'" + employee.getEducation() + "'");}
                if(!employee.getSpeciality().equals("")){VALUES("speciality", "'" + employee.getSpeciality() + "'");}
                if(!employee.getHobby().equals("")){VALUES("hobby", "'" + employee.getHobby() + "'");}
                if(!employee.getRemark().equals("")){VALUES("remark", "'" + employee.getRemark() + "'");}
                VALUES("create_date", "'" + new SimpleDateFormat("yyyy-MM-dd").format(employee.getCreateDate()) + "'");
            }
        }.toString();
    }

    public String insertByParam(Map<String,String> param){
        Map<String,String> sqlMapping = TableTitle.employeeSqlMapping();
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
                            case "cardId":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "address":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "postCode":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "tel":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "phone":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "qqNum":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "email":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "sex":VALUES(sqlMapping.get(s), param.get(s));;break;
                            case "party":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "birthday":VALUES(sqlMapping.get(s),  "'"+param.get(s)+"'");;break;
                            case "race":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "education":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "speciality":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "hobby":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "remark":VALUES(sqlMapping.get(s), "'"+param.get(s)+"'");;break;
                            case "createDate":VALUES("create_date","'" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "'");break;
                        }
                    }
                }
                
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
        log.info(param);
        if(param.get("id").equals("")){return "";}
        Map<String,String> sqlMapping = TableTitle.employeeSqlMapping();
        return new SQL(){
            {
                UPDATE("employee_inf");
                for(String s : param.keySet()){
                    if(!param.get(s).equals("")){
                        switch (s){
                            case "name":SET(sqlMapping.get(s) + " = " + " '"+param.get(s)+"'");;break;
                            case "dept":SET(sqlMapping.get(s) + " = " + param.get(s));;break;
                            case "job":SET(sqlMapping.get(s) + " = " + param.get(s));;break;
                            case "cardId":SET(sqlMapping.get(s) + " = "+ " '" + param.get(s)+"'");;break;
                            case "address":SET(sqlMapping.get(s) + " = " + "'"+param.get(s)+"'");;break;
                            case "postCode":SET(sqlMapping.get(s) + " = "+ " '" + param.get(s)+"'");;break;
                            case "tel":SET(sqlMapping.get(s) + " = "+ " '" + param.get(s)+"'");;break;
                            case "phone":SET(sqlMapping.get(s) + " = "+ " '" + param.get(s)+"'");;break;
                            case "qqNum":SET(sqlMapping.get(s) + " = "+ " '" + param.get(s)+"'");;break;
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
    public String getEmployeeListByParam(Map<String,String> param){
        int pageSize = Integer.parseInt(param.get("pageSize"));
        String r = new SQL(){
            {
                SELECT("id,name,dept_id,job_id,phone");
                FROM("employee_inf");
                WHERE("id>=("+ getSelectId(new HashMap<String,String>(param))+")");
                param.remove("pageNo");
                param.remove("pageSize");
                for(String key : param.keySet()){
                    if(!param.get(key).equals("")){
                        AND();
                        WHERE(TableTitle.employeeSqlMapping().get(key) + " LIKE '%"+ param.get(key) +"%'");
                    }
                }
            }
        }.toString() + " limit " + pageSize;
        log.info(r);
        return r;
    }
    private String getSelectId(Map<String,String> param){
        int pageSize = Integer.parseInt(param.get("pageSize"));
        int start =pageSize * (Integer.parseInt(param.get("pageNo"))  - 1);
        return new SQL(){
            {
                SELECT("id");
                FROM("notice_inf");
                param.remove("pageNo");
                param.remove("pageSize");
                for(String key : param.keySet()){
                    if(!param.get(key).equals("")){
                        AND();
                        WHERE(TableTitle.employeeSqlMapping().get(key) + " LIKE '%"+ param.get(key) +"%'");
                    }
                }
            }
        }.toString()+" limit "+start+",1";
    }
    public String getCount(Map<String, String> param){
        String r = new SQL(){
            {
                SELECT("COUNT(id)");
                FROM("employee_inf");
                for(String key : param.keySet()){
                    if(!param.get(key).equals("")){
                        AND();
                        WHERE(TableTitle.employeeSqlMapping().get(key) + " LIKE '%"+ param.get(key) +"%'");
                    }
                }
            }
        }.toString();
        log.info(r);
        return r;
    }
}
