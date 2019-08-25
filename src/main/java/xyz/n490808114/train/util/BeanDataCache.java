package xyz.n490808114.train.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import xyz.n490808114.train.service.HrmService;
import xyz.n490808114.train.domain.*;

import java.util.*;


/**
 * 
 */
@Service
public class BeanDataCache {
    private static Log  log = LogFactory.getLog(BeanDataCache.class);

    @Autowired
    @Qualifier("hrmServiceImpl")
    private HrmService hrmService;

    private static Map<String,String> deptMap = new LinkedHashMap<>();
    private static Boolean isExpiredOfDeptCache = true;
    private static Map<String,String> jobMap = new LinkedHashMap<>();
    private static Boolean isExpiredOfJobCache = true;

    public static void setDeptCacheExpired(){
        new Thread(() ->{
                synchronized(isExpiredOfDeptCache){
                    isExpiredOfDeptCache = true;
            }
        }).run();
    }

    public static void setJobCacheExpired(){
        new Thread(() ->{
            synchronized(isExpiredOfJobCache){
                isExpiredOfJobCache = true;
        }
    }).run();
    }


    /**
     * 获取JobMap 如果为空，执行init()初始化并等待200ms,如果还为空，循环验证是否为空（即是否初始化成功）并等待200ms;
     */
    public Map<String,String> getJobMap()  {
        if(isExpiredOfJobCache){
            synchronized(isExpiredOfJobCache){
                reLoadJobMap();
                isExpiredOfJobCache = false;
            }
        }
        return jobMap;
    }
    /**
     * 获取DeptMap 如果为空，执行init()初始化并等待200ms,如果还为空，循环验证是否为空（即是否初始化成功）并等待200ms;
     */
    public Map<String,String> getDeptMap(){
        if(isExpiredOfDeptCache){
            synchronized(isExpiredOfDeptCache){
                reLoadDeptMap();
                isExpiredOfDeptCache = false;
            }
        }
        return deptMap;
    }
    public void reLoadJobMap(){
        log.info("开始更新JobMap");
        List<Job> jobs = hrmService.findAllJob();
        Map<String,String> map = new LinkedHashMap<>();
        for(Job job : jobs){
            map.put("" + job.getId(),job.getName());
        }
        jobMap = map;
        log.info(map);
        log.info("JobMap更新完毕");
    }
    public void reLoadDeptMap(){
        log.info("开始更新DeptMap");
        List<Dept> depts = hrmService.findAllDept();
        Map<String,String> map = new LinkedHashMap<>();
        for(Dept dept : depts){
            map.put("" + dept.getId(), dept.getName());
        }
        deptMap = map;
        log.info(map);
        log.info("DeptMap更新完毕");
    }
}
