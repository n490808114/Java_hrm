package xyz.n490808114.train.controller;

import java.util.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import xyz.n490808114.train.domain.Job;
import xyz.n490808114.train.service.*;
import xyz.n490808114.train.util.TableTitle;
import xyz.n490808114.train.util.TrainConstants;
import xyz.n490808114.train.service.HrmService;


@RestController
@RequestMapping("/job")
public class JobController{
    private static Log log = LogFactory.getLog(JobController.class);
    @Autowired
    @Qualifier("hrmServiceImpl")
    HrmService hrmService;

    @Autowired
    BeanDataCache beanDataCache;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @GetMapping
    public String getList(@RequestParam(value = "pageNo",defaultValue = "1")int pageNo,
                          @RequestParam(value = "pageSize",defaultValue = "20")int pageSize,
                          @RequestParam Map<String,String> requestParam){
        Map<String, Object> map = new HashMap<>();
        map.put("title","job");
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        map.put("code",200);
        map.put("message","获取成功");
        map.put("dataTitle",TableTitle.jobListTitle());
        Map<String,Job> cacheMap = beanDataCache.getJobMap();
        List<Job> data = new LinkedList<>();
        int mapCount = 0;
        int start = (pageNo - 1) * pageSize;
        String name = requestParam.get("name");
        if(name != null && !name.equals("")){
            int count = 0;
            for(String key : cacheMap.keySet()){
                if(cacheMap.get(key).getName().contains(name)){
                    if(mapCount == start && data.size() < pageSize){
                        data.add(cacheMap.get(key));
                    }else{
                        mapCount++;
                    }
                    count++;
                }
            }
            map.put("count",count);
            map.put("data",data);
        }else{
            for(String key : cacheMap.keySet()){
                if(mapCount == start && data.size() < pageSize){
                    data.add(cacheMap.get(key));
                }else if(data.size() == pageSize){
                    break;
                }else{
                    mapCount++;
                }
            }
            map.put("count",cacheMap.size());
            map.put("data",data);
        }
        String s = JSON.toJSONString(map);
        log.info(s);
        return s;
    }
    @GetMapping("/create")
    public Map<String,Object> create(){
        Map<String,Object> map = new HashMap<>();
        map.put("title","job");
        map.put("dataTitle",TableTitle.jobCreateTitle());
        log.info(map);
        return map;
    }
    @GetMapping("/search")
    public Map<String,Object> search(){
        Map<String,Object> map = new HashMap<>();
        map.put("title","job");
        map.put("dataTitle",TableTitle.jobSearchTitle());
        log.info(map);
        return map;
    }
    @GetMapping("/{id}")
    public String getDetail(@PathVariable("id") int id){
        Map<String,Object> map = new HashMap<>();
        map.put("title","job");

        Job job = hrmService.findJobById(id);
        if(job == null){
            map.put("code",404);
            map.put("message","找不到这个部门");
            return JSON.toJSONString(map);
        }else{
            map.put("code",200);
            map.put("message","获取成功");
            map.put("dataTitle",TableTitle.jobTitle());
            map.put("data",job);
            PropertyFilter propertyFilter = (Object object,String name,Object value) ->{
                if("employees".equals(name)){
                    return false;
                }else if("id".equals(name)){
                    return false;
                }
                return true;
            };
            String s = JSON.toJSONString(map,propertyFilter);
            log.info(s);
            return s;
        }
    }
    @PostMapping
    public Map<String,Object> create(@RequestParam Map<String,String> param){
        Job job = new Job();
        job.setName(param.get("name"));
        job.setRemark(param.get("remark"));

        Set<ConstraintViolation<Job>> set = validator.validate(job);
        Map<String,Object> map = new HashMap<>();
        if(set.size() == 0){
            hrmService.addJob(job);
            map.put("code",200);
            map.put("message","创建成功");
        }else{
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Job> constraintViolation : set){
                error.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            map.put("code", 404);
            map.put("message",error);
        }
        log.info(map);
        return map;
    }
    @PutMapping("/{id}")
    public Map<String,Object> update(@PathVariable("id") int id,@RequestParam Map<String,String> param){
        Job job = new Job();
        job.setName(param.get("name"));
        job.setRemark(param.get("remark"));

        Set<ConstraintViolation<Job>> set = validator.validate(job);
        Map<String,Object> map = new HashMap<>();
        if(set.size() == 0){
            job.setId(id);
            hrmService.modifyJob(job);;
            map.put("code",200);
            map.put("message","修改成功");
        }else{
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Job> constraintViolation : set){
                error.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            map.put("code", 404);
            map.put("message",error);
        }
        log.info(map);
        return map;
    }
    @DeleteMapping("/{id}")
    public Map<String,Object> delete(@PathVariable("id") int id){
        Map<String,Object> map = new HashMap<>();
        if(beanDataCache.getJobMap().get(""+id) == null){
            map.put("code","404");
            map.put("message","错误的部门序号");
        }else if(hrmService.countEmployeesByJobId(id) > 0){
            map.put("code","404");
            map.put("message","该部门内还有员工,请先调整他们的部门后再进行删除");
        }else{
            hrmService.removeJob(id);
            map.put("code","200");
            map.put("message","删除成功");
        }
        log.info(map);
        return map;
    }

}