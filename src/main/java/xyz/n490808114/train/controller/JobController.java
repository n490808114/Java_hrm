package xyz.n490808114.train.controller;

import java.util.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import xyz.n490808114.train.domain.Job;
import xyz.n490808114.train.dto.DetailDto;
import xyz.n490808114.train.dto.ListDto;
import xyz.n490808114.train.dto.SimpleDto;
import xyz.n490808114.train.dto.TitleDto;
import xyz.n490808114.train.service.*;
import xyz.n490808114.train.util.TableTitle;
import xyz.n490808114.train.service.HrmService;


@RestController
@RequestMapping("/job")
@PreAuthorize("hasRole('ADMIN')")
public class JobController{
    private static Log log = LogFactory.getLog(JobController.class);
    @Autowired
    @Qualifier("hrmServiceImpl")
    HrmService hrmService;

    @Autowired
    BeanDataCache beanDataCache;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @GetMapping
    public ListDto<Job> getList(@RequestParam Map<String,String> param){
        RequestParamCheck.check(param);
        return hrmService.getJobList(param);
        
    }
    @GetMapping("/create")
    public TitleDto create(){
        return new TitleDto().setTitle("job").setDataTitle(TableTitle.JOB_CREATE_TITLE);

    }
    @GetMapping("/search")
    public TitleDto search(){
        return new TitleDto().setTitle("job").setDataTitle(TableTitle.JOB_SEARCH_TITLE);
    }
    @GetMapping("/{id}")
    public DetailDto<Job> getDetail(@PathVariable("id") int id){
        DetailDto<Job> dto;

        Job job = hrmService.findJobById(id);
        if(job == null){
            dto = new DetailDto<Job>().builder().code(404).message("找不到这个部门").build();
        }else{
            dto = new DetailDto<Job>().builder().code(200)
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
    public SimpleDto create(@RequestParam Map<String,String> param){
        Job job = new Job();
        job.setName(param.get("name"));
        job.setRemark(param.get("remark"));

        Set<ConstraintViolation<Job>> set = validator.validate(job);
        if(set.size() == 0){
            hrmService.addJob(job);
            return new SimpleDto(200,"创建成功");
        }else{
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Job> constraintViolation : set){
                error.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            return new SimpleDto(404,error);

        }
    }
    @PutMapping("/{id}")
    public SimpleDto update(@PathVariable("id") int id,@RequestParam Map<String,String> param){
        Job job = new Job();
        job.setName(param.get("name"));
        job.setRemark(param.get("remark"));

        Set<ConstraintViolation<Job>> set = validator.validate(job);
        if(set.size() == 0){
            job.setId(id);
            hrmService.modifyJob(job);
            return new SimpleDto(200,"修改成功");
        }else{
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Job> constraintViolation : set){
                error.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            return new SimpleDto(404,error);
        }
    }
    @DeleteMapping("/{id}")
    public SimpleDto delete(@PathVariable("id") int id){
        if(beanDataCache.getJobMap().get(""+id) == null){
            return new SimpleDto(404,"错误的部门序号");
        }else if(hrmService.countEmployeesByJobId(id) > 0){
            return new SimpleDto(404,"该部门内还有员工,请先调整他们的部门后再进行删除");
        }else{
            hrmService.removeJobById(id);
            return new SimpleDto(200,"删除成功");
        }
    }

}