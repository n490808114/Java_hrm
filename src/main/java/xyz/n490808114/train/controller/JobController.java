package xyz.n490808114.train.controller;

import java.util.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import xyz.n490808114.train.domain.Job;
import xyz.n490808114.train.dto.DetailDto;
import xyz.n490808114.train.dto.ListDto;
import xyz.n490808114.train.service.*;
import xyz.n490808114.train.util.TableTitle;
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
    public ListDto<Job> getList(@RequestParam(value = "pageNo",defaultValue = "1")int pageNo,
                          @RequestParam(value = "pageSize",defaultValue = "20")int pageSize,
                          @RequestParam Map<String,String> requestParam){

        ListDto<Job> dto = null;

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
            /*
            if(data.size() != 0){
                dto = new ListDto<>(200,"获取成功","job",
                                        pageNo,pageSize,count,
                                        TableTitle.JOB_LIST_TITLE,
                                        data);
            }*/
        }else{
            for(String key : cacheMap.keySet()){
                if(mapCount == start && data.size() < pageSize){
                    data.add(cacheMap.get(key));
                }else if(data.size() == pageSize){
                    break;
                }else{
                    mapCount++;
                }
            }/*
            if(data.size() != 0){
                dto = new ListDto<>(200,"获取成功","job",
                                        pageNo,pageSize,cacheMap.size(),
                                        TableTitle.JOB_LIST_TITLE,
                                        data);
            }*/
        }
        if(data.size() == 0){dto = new ListDto<>(404,"找不到任何的部门");}
        log.info(dto);
        return dto;
    }
    @GetMapping("/create")
    public Map<String,Object> create(){
        Map<String,Object> map = new HashMap<>();
        map.put("title","job");
        map.put("dataTitle",TableTitle.JOB_CREATE_TITLE);
        log.info(map);
        return map;
    }
    @GetMapping("/search")
    public Map<String,Object> search(){
        Map<String,Object> map = new HashMap<>();
        map.put("title","job");
        map.put("dataTitle",TableTitle.JOB_SEARCH_TITLE);
        log.info(map);
        return map;
    }
    @GetMapping("/{id}")
    public DetailDto getDetail(@PathVariable("id") int id){
        DetailDto dto = null;

        Job job = hrmService.findJobById(id);
        if(job == null){
            dto = new DetailDto().builder().code(404).message("找不到这个部门").build();
        }else{
            dto = new DetailDto().builder().code(200)
                                        .message("获取成功")
                                        .title("job")
                                        .dataTitle(TableTitle.JOB_TITLE)
                                        .data(job)
                                        .build();
        }
        log.info(dto);
        return dto;
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