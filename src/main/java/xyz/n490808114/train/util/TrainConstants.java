package xyz.n490808114.train.util;

import java.util.HashMap;
import java.util.Map;

public class TrainConstants {
    public static final String USER_TABLE="user_inf";
    public static final String DEPT_TABLE = "dept_inf";
    public static final String JOB_TABLE = "job_inf";
    public static final String EMPLOYEE_TABLE = "employee_inf";
    public static final String NOTICE_TABLE = "notice_inf";
    public static final String DOCUMENT_TABLE = "document_inf";

    public static final String LOGIN = "loginForm";
    public static final String USER_REQUEST = "user_request";
    public static int PAGE_DEFAULT_SIZE = 4;
    public static String DEFAULT_PAGESIZE = "20";

    public static final String USER_DEFAULT_NAME = "User";

    public static final String JWT_SECRET = "FeiLiuZhiXiaSanQianChiYiShiYinHeLuoJiuTian";

    public static final Map<String,Integer> ROLE_LEVELS;
    static{
        ROLE_LEVELS = new HashMap<>();
        ROLE_LEVELS.put("ROLE_ADMIN",2);
        ROLE_LEVELS.put("ROLE_USER",1);
    }
}
