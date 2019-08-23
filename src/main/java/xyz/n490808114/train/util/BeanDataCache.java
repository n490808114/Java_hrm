package xyz.n490808114.train.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import xyz.n490808114.train.domain.Dept;
import xyz.n490808114.train.domain.Job;
import xyz.n490808114.train.service.HrmService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class BeanDataCache {
    private static Long lastUpdateTime = 0L;
    private static final long EXPIRED_TIME = 3600000L;
    @Autowired
    @Qualifier("hrmServiceImpl")
    private HrmService hrmService;

    private static Map<String,String> deptMap = new HashMap<>();
    private static Map<String,String> jobMap = new HashMap<>();

    public  Map<String, String> getJobMap()  {
        while(isExpired()){
            reLoad();
            try{
                Thread.sleep(1000);
            }catch (InterruptedException ignored){}
        }
        return jobMap;
    }
    public Map<String,String> getDeptMap(){
        while(isExpired()){
            reLoad();
            try{
                Thread.sleep(1000);
            }catch (InterruptedException ignored){}
        }
        return deptMap;
    }
    private void reLoad(){
        synchronized (lastUpdateTime){
            if(isExpired()){
                loadMaps();
                lastUpdateTime = new Date().getTime();
            }
        }
    }
    private void loadMaps(){
        List<Dept> depts = hrmService.findAllDept();
        for(Dept dept : depts){
            while(true){
                try{
                    deptMap.put(""+dept.getId(), dept.getName());
                    break;
                }catch(Exception ignored){}
            }

        }
        List<Job> jobs = hrmService.findAllJob();
        for(Job job : jobs){
            while(true){
                try{
                    jobMap.put(""+job.getId(),job.getName());
                    break;
                }catch(Exception ignored){}
            }
        }
    }
    private boolean isExpired(){
        return (new Date().getTime() - lastUpdateTime) > EXPIRED_TIME;
    }
}
