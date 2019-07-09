package xyz.n490808114.domain;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TableTitle {

    private static final Map<String,String> noticeTitle = new LinkedHashMap<>();
    private static final Map<String,String> noticeListTitle = new LinkedHashMap<>();
    private static final Map<String,String> noticeCreateTitle = new LinkedHashMap<>();

    private static final Map<String,String> employeeTitle = new LinkedHashMap<>();
    private static final Map<String,String> employeeListTitle = new LinkedHashMap<>();
    private static final Map<String,String> employeeCreateTitle = new LinkedHashMap<>();

    static {
        noticeTitle.put("id","序号");
        noticeTitle.put("title","标题");
        noticeTitle.put("content","公告内容");
        noticeTitle.put("createDate","最后更改日期");
        noticeTitle.put("user","创建人");
        
        noticeListTitle.put("id","序号");
        noticeListTitle.put("title","标题");
        noticeListTitle.put("createDate","最后更改日期");
        noticeListTitle.put("user","创建人");

        noticeCreateTitle.put("title","标题");
        noticeCreateTitle.put("content","公告内容");
    }
    static{
        employeeTitle.put("id", "序号");
        employeeTitle.put("name", "姓名");
        employeeTitle.put("sex","性别");
        employeeTitle.put("dept", "部门");
        employeeTitle.put("job","职位");
        employeeTitle.put("cardId","身份证号");
        employeeTitle.put("address","联系地址");
        employeeTitle.put("postCode","邮编");
        employeeTitle.put("tel","固定电话");
        employeeTitle.put("phone", "手机号码");
        employeeTitle.put("qqNum","QQ号码");
        employeeTitle.put("email","邮箱地址");
        employeeTitle.put("party","政治身份");
        employeeTitle.put("birthday","出生日期");
        employeeTitle.put("race","民族");
        employeeTitle.put("education","教育程度");
        employeeTitle.put("speciality","特长");
        employeeTitle.put("hobby","爱好");
        employeeTitle.put("remark","备注");
        employeeTitle.put("createDate","创建日期");


        employeeListTitle.put("id", "员工号");
        employeeListTitle.put("name", "姓名");
        employeeListTitle.put("dept", "部门");
        employeeListTitle.put("job","职位");
        employeeListTitle.put("phone", "电话");

        employeeCreateTitle.put("name", "姓名");
        employeeCreateTitle.put("sex","性别");
        employeeCreateTitle.put("dept", "部门");
        employeeCreateTitle.put("job","职位");
        employeeCreateTitle.put("cardId","身份证号");
        employeeCreateTitle.put("address","联系地址");
        employeeCreateTitle.put("postCode","邮编");
        employeeCreateTitle.put("tel","固定电话");
        employeeCreateTitle.put("phone", "手机号码");
        employeeCreateTitle.put("qqNum","QQ号码");
        employeeCreateTitle.put("email","邮箱地址");
        employeeCreateTitle.put("party","政治身份");
        employeeCreateTitle.put("birthday","出生日期");
        employeeCreateTitle.put("race","民族");
        employeeCreateTitle.put("education","教育程度");
        employeeCreateTitle.put("speciality","特长");
        employeeCreateTitle.put("hobby","爱好");
        employeeCreateTitle.put("remark","备注");
    }
    public static Map<String, String> noticeTitle(){return noticeTitle;}
    public static Map<String,String> noticeListTitle(){return noticeListTitle;}
    public static Map<String,String> noticeCreateTitle(){return noticeCreateTitle;}

    public static Map<String,String> employeeTitle(){return employeeTitle;}
    public static Map<String,String> employeeListTitle(){return employeeListTitle;}
    public static Map<String,String> employeeCreateTitle(){return employeeCreateTitle;}
}
