package xyz.n490808114.train.service;

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

    private static Map<String,Dept> deptMap = new LinkedHashMap<>();
    private static Boolean isExpiredOfDeptCache = true;
    private static Map<String,Job> jobMap = new LinkedHashMap<>();
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
    public Map<String,Job> getJobMap()  {
        if(isExpiredOfJobCache){
            synchronized(isExpiredOfJobCache){
                List<Job> list = hrmService.findAllJob();
                Map<String,Job> newMap = new LinkedHashMap<>();
                for(Job job:list){
                    newMap.put("" + job.getId(), job);
                }
                log.info("更新JobCache:"+newMap);
                jobMap = newMap;
                isExpiredOfJobCache = false;
            }
        }
        return jobMap;
    }
    /**
     * 获取DeptMap 如果为空，执行init()初始化并等待200ms,如果还为空，循环验证是否为空（即是否初始化成功）并等待200ms;
     */
    public Map<String,Dept> getDeptMap(){
        if(isExpiredOfDeptCache){
            synchronized(isExpiredOfDeptCache){
                List<Dept> list = hrmService.findAllDept();
                Map<String,Dept> newMap = new LinkedHashMap<>();
                for(Dept dept:list){
                    newMap.put(""+dept.getId(), dept);
                }
                log.info("更新DeptCache:"+newMap);
                deptMap = newMap;
                isExpiredOfDeptCache = false;
            }
        }
        return deptMap;
    }
}
