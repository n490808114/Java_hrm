package xyz.n490808114.train.util;

import java.util.*;

import xyz.n490808114.train.domain.Sex;
public class TableTitle {

    public static final Map<String,String> NOTICE_TITLE = new LinkedHashMap<>();
    public static final Map<String,String> NOTICE_LIST_TITLE = new LinkedHashMap<>();
    public static final Map<String,String> NOTICE_CREATE_TITLE = new LinkedHashMap<>();
    public static final Map<String,String> NOTICE_SEARCH_TITLE = new LinkedHashMap<>();

    public static final Map<String,String> EMPLOYEE_TITLE = new LinkedHashMap<>();
    public static final Map<String,String> EMPLOYEE_LIST_TITLE = new LinkedHashMap<>();
    public static final Map<String,String> EMPLOYEE_CREATE_TITLE = new LinkedHashMap<>();
    public static final Map<String,String> EMPLOYEE_SEARCH_TITLE = new LinkedHashMap<>();
    public static final Map<String,Sex> SEX_MAP = new LinkedHashMap<>();
    public static final Map<String,String> EMPLOYEE_SQL_MAPPING = new HashMap<>();

    public static final Map<String,String> DEPT_TITLE = new LinkedHashMap<>();
    public static final Map<String,String> DEPT_LIST_TITLE = new LinkedHashMap<>();
    public static final Map<String,String> DEPT_CREATE_TITLE = new LinkedHashMap<>();
    public static final Map<String,String> DEPT_SEARCH_TITLE = new LinkedHashMap<>();

    public static final Map<String,String> JOB_TITLE = new LinkedHashMap<>();
    public static final Map<String,String> JOB_LIST_TITLE = new LinkedHashMap<>();
    public static final Map<String,String> JOB_CREATE_TITLE = new LinkedHashMap<>();
    public static final Map<String,String> JOB_SEARCH_TITLE = new LinkedHashMap<>();

    static {
        NOTICE_TITLE.put("id","序号");
        NOTICE_TITLE.put("title","标题");
        NOTICE_TITLE.put("content","公告内容");
        NOTICE_TITLE.put("user","创建人");
        
        NOTICE_LIST_TITLE.put("id","序号");
        NOTICE_LIST_TITLE.put("title","标题");
        NOTICE_LIST_TITLE.put("createDate","创建日期");
        NOTICE_LIST_TITLE.put("user","创建人");

        NOTICE_CREATE_TITLE.put("title","标题");
        NOTICE_CREATE_TITLE.put("content","公告内容");

        NOTICE_SEARCH_TITLE.put("title","标题");
    }
    static{
        EMPLOYEE_TITLE.put("name", "姓名");
        EMPLOYEE_TITLE.put("sex","性别");
        EMPLOYEE_TITLE.put("dept", "部门");
        EMPLOYEE_TITLE.put("job","职位");
        EMPLOYEE_TITLE.put("cardId","身份证号");
        EMPLOYEE_TITLE.put("address","联系地址");
        EMPLOYEE_TITLE.put("postCode","邮编");
        EMPLOYEE_TITLE.put("tel","固定电话");
        EMPLOYEE_TITLE.put("phone", "手机号码");
        EMPLOYEE_TITLE.put("qqNum","QQ号码");
        EMPLOYEE_TITLE.put("email","邮箱地址");
        EMPLOYEE_TITLE.put("party","政治身份");
        EMPLOYEE_TITLE.put("birthday","出生日期");
        EMPLOYEE_TITLE.put("race","民族");
        EMPLOYEE_TITLE.put("education","教育程度");
        EMPLOYEE_TITLE.put("speciality","特长");
        EMPLOYEE_TITLE.put("hobby","爱好");
        EMPLOYEE_TITLE.put("remark","备注");

        EMPLOYEE_SEARCH_TITLE.put("id","序号");
        EMPLOYEE_SEARCH_TITLE.put("name", "姓名");
        EMPLOYEE_SEARCH_TITLE.put("sex","性别");
        EMPLOYEE_SEARCH_TITLE.put("dept", "部门");
        EMPLOYEE_SEARCH_TITLE.put("job","职位");
        EMPLOYEE_SEARCH_TITLE.put("cardId","身份证号");
        EMPLOYEE_SEARCH_TITLE.put("phone", "手机号码");
        EMPLOYEE_SEARCH_TITLE.put("email","邮箱地址");


        EMPLOYEE_LIST_TITLE.put("id", "员工号");
        EMPLOYEE_LIST_TITLE.put("name", "姓名");
        EMPLOYEE_LIST_TITLE.put("dept", "部门");
        EMPLOYEE_LIST_TITLE.put("job","职位");
        EMPLOYEE_LIST_TITLE.put("phone", "电话");

        EMPLOYEE_CREATE_TITLE.put("name", "姓名");
        EMPLOYEE_CREATE_TITLE.put("sex","性别");
        EMPLOYEE_CREATE_TITLE.put("dept", "部门");
        EMPLOYEE_CREATE_TITLE.put("job","职位");
        EMPLOYEE_CREATE_TITLE.put("cardId","身份证号");
        EMPLOYEE_CREATE_TITLE.put("address","联系地址");
        EMPLOYEE_CREATE_TITLE.put("postCode","邮编");
        EMPLOYEE_CREATE_TITLE.put("tel","固定电话");
        EMPLOYEE_CREATE_TITLE.put("phone", "手机号码");
        EMPLOYEE_CREATE_TITLE.put("qqNum","QQ号码");
        EMPLOYEE_CREATE_TITLE.put("email","邮箱地址");
        EMPLOYEE_CREATE_TITLE.put("party","政治身份");
        EMPLOYEE_CREATE_TITLE.put("birthday","出生日期");
        EMPLOYEE_CREATE_TITLE.put("race","民族");
        EMPLOYEE_CREATE_TITLE.put("education","教育程度");
        EMPLOYEE_CREATE_TITLE.put("speciality","特长");
        EMPLOYEE_CREATE_TITLE.put("hobby","爱好");
        EMPLOYEE_CREATE_TITLE.put("remark","备注");

        EMPLOYEE_SQL_MAPPING.put("id", "id");
        EMPLOYEE_SQL_MAPPING.put("dept", "dept_id");
        EMPLOYEE_SQL_MAPPING.put("job", "job_id");
        EMPLOYEE_SQL_MAPPING.put("name", "name");
        EMPLOYEE_SQL_MAPPING.put("cardId", "card_id");
        EMPLOYEE_SQL_MAPPING.put("address", "address");
        EMPLOYEE_SQL_MAPPING.put("postCode", "post_code");
        EMPLOYEE_SQL_MAPPING.put("tel", "tel");
        EMPLOYEE_SQL_MAPPING.put("phone", "phone");
        EMPLOYEE_SQL_MAPPING.put("qqNum", "qq_num");
        EMPLOYEE_SQL_MAPPING.put("email", "email");
        EMPLOYEE_SQL_MAPPING.put("sex", "sex");
        EMPLOYEE_SQL_MAPPING.put("party", "party");
        EMPLOYEE_SQL_MAPPING.put("birthday", "birthday");
        EMPLOYEE_SQL_MAPPING.put("race", "race");
        EMPLOYEE_SQL_MAPPING.put("education", "education");
        EMPLOYEE_SQL_MAPPING.put("speciality", "speciality");
        EMPLOYEE_SQL_MAPPING.put("hobby", "hobby");
        EMPLOYEE_SQL_MAPPING.put("remark", "remark");
        EMPLOYEE_SQL_MAPPING.put("createDate", "create_date");

        SEX_MAP.put("0",new Sex(0,"女"));
        SEX_MAP.put("1",new Sex(1,"男"));
        SEX_MAP.put("2",new Sex(2,"其他"));
    }
    static {
        DEPT_TITLE.put("name", "部门名称");
        DEPT_TITLE.put("remark", "介绍");

        DEPT_LIST_TITLE.put("name", "部门名称");
        DEPT_LIST_TITLE.put("remark", "介绍");

        DEPT_CREATE_TITLE.put("name", "部门名称");
        DEPT_CREATE_TITLE.put("remark", "介绍");

        DEPT_SEARCH_TITLE.put("name", "部门名称");
    }
    static{
        JOB_TITLE.put("name", "职位名称");
        JOB_TITLE.put("remark", "介绍");

        JOB_LIST_TITLE.put("name", "职位名称");
        JOB_LIST_TITLE.put("remark", "介绍");

        JOB_CREATE_TITLE.put("name", "职位名称");
        JOB_CREATE_TITLE.put("remark", "介绍");

        JOB_SEARCH_TITLE.put("name", "职位名称");
    }
}
