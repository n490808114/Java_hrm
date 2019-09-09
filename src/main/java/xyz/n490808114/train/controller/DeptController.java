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

import xyz.n490808114.train.domain.Dept;
import xyz.n490808114.train.dto.DetailDto;
import xyz.n490808114.train.dto.ListDto;
import xyz.n490808114.train.dto.SimpleDto;
import xyz.n490808114.train.dto.TitleDto;
import xyz.n490808114.train.service.*;
import xyz.n490808114.train.util.TableTitle;
import xyz.n490808114.train.service.HrmService;


@RestController
@RequestMapping("/dept")
@PreAuthorize("hasRole('ADMIN')")
public class DeptController{
    private static Log log = LogFactory.getLog(DeptController.class);
    @Autowired
    @Qualifier("hrmServiceImpl")
    HrmService hrmService;

    @Autowired
    BeanDataCache beanDataCache;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @GetMapping
    public ListDto<Dept> getList(@RequestParam Map<String,String> param){
        RequestParamCheck.check(param);
        return hrmService.getDeptList(param);
        
    }
    @GetMapping("/create")
    public TitleDto create(){
        return new TitleDto().setTitle("dept").setDataTitle(TableTitle.DEPT_CREATE_TITLE);

    }
    @GetMapping("/search")
    public TitleDto search(){
        return new TitleDto().setTitle("dept").setDataTitle(TableTitle.DEPT_SEARCH_TITLE);
    }
    @GetMapping("/{id}")
    public DetailDto<Dept> getDetail(@PathVariable("id") int id){
        DetailDto<Dept> dto;

        Dept dept = hrmService.findDeptById(id);
        if(dept == null){
            dto = new DetailDto<Dept>().builder().code(404).message("找不到这个部门").build();
        }else{
            dto = new DetailDto<Dept>().builder().code(200)
                                        .message("获取成功")
                                        .title("dept")
                                        .dataTitle(TableTitle.DEPT_TITLE)
                                        .data(dept)
                                        .build();
        }
        log.info(dto);
        return dto;
    }
    @PostMapping
    public SimpleDto create(@RequestParam Map<String,String> param){
        Dept dept = new Dept();
        dept.setName(param.get("name"));
        dept.setRemark(param.get("remark"));

        Set<ConstraintViolation<Dept>> set = validator.validate(dept);
        if(set.size() == 0){
            hrmService.addDept(dept);
            return new SimpleDto(200,"创建成功");
        }else{
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Dept> constraintViolation : set){
                error.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            return new SimpleDto(404,error);

        }
    }
    @PutMapping("/{id}")
    public SimpleDto update(@PathVariable("id") int id,@RequestParam Map<String,String> param){
        Dept dept = new Dept();
        dept.setName(param.get("name"));
        dept.setRemark(param.get("remark"));

        Set<ConstraintViolation<Dept>> set = validator.validate(dept);
        if(set.size() == 0){
            dept.setId(id);
            hrmService.modifyDept(dept);
            return new SimpleDto(200,"修改成功");
        }else{
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Dept> constraintViolation : set){
                error.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            return new SimpleDto(404,error);
        }
    }
    @DeleteMapping("/{id}")
    public SimpleDto delete(@PathVariable("id") int id){
        if(beanDataCache.getDeptMap().get(""+id) == null){
            return new SimpleDto(404,"错误的部门序号");
        }else if(hrmService.countEmployeesByDeptId(id) > 0){
            return new SimpleDto(404,"该部门内还有员工,请先调整他们的部门后再进行删除");
        }else{
            hrmService.removeDeptById(id);
            return new SimpleDto(200,"删除成功");
        }
    }

}