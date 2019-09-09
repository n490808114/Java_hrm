package xyz.n490808114.train.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.n490808114.train.domain.Employee;
import xyz.n490808114.train.dto.DetailDto;
import xyz.n490808114.train.dto.ListDto;
import xyz.n490808114.train.dto.SimpleDto;
import xyz.n490808114.train.dto.TitleDto;
import xyz.n490808114.train.service.BeanDataCache;
import xyz.n490808114.train.service.HrmService;
import xyz.n490808114.train.service.RequestParamCheck;
import xyz.n490808114.train.util.TableTitle;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;

@RestController
@RequestMapping("/employee")
@PreAuthorize("hasRole('USER')")
public class EmployeeController {
    private static Log log = LogFactory.getLog(EmployeeController.class);
    @Autowired
    @Qualifier("hrmServiceImpl")
    HrmService hrmService;

    @Autowired
    BeanDataCache beanDataCache;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @GetMapping
    public ListDto<Employee> getList(@RequestParam Map<String,String> param) {
        RequestParamCheck.check(param);
        return hrmService.getEmployeeList(param);
    }

    @GetMapping("/create")
    public TitleDto create(){
        return new TitleDto().setTitle("employee").setDataTitle(TableTitle.EMPLOYEE_CREATE_TITLE)
                                    .setSexData(TableTitle.SEX_MAP)
                                    .setDeptData(beanDataCache.getDeptMap())
                                    .setJobData(beanDataCache.getJobMap());
    }
    @GetMapping("/search")
    public TitleDto search(){
        return new TitleDto().setTitle("employee").setDataTitle(TableTitle.EMPLOYEE_SEARCH_TITLE)
                                    .setSexData(TableTitle.SEX_MAP)
                                    .setDeptData(beanDataCache.getDeptMap())
                                    .setJobData(beanDataCache.getJobMap());

    }
    @PostMapping
    public SimpleDto create(@RequestParam Map<String,String> param){
        Employee employee = new Employee(param);
        Set<ConstraintViolation<Employee>> set = validator.validate(employee);

        if(set.size() == 0){
            employee.setCreateDate(new Date());
            hrmService.addEmployee(employee);
            return new SimpleDto(200,"创建成功");
        }else{
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Employee> constraintViolation : set){
                error.put(constraintViolation.getPropertyPath().toString()
                , constraintViolation.getMessage());
            }
            return new SimpleDto(404,error);
        }
    }


    @GetMapping("/{id}")
    public DetailDto<Employee> getDetail(@PathVariable("id") int id){
        DetailDto<Employee> dto;
        Map<String,Object> map = new HashMap<>();
        map.put("title","employee");
        Employee employee = hrmService.findEmployeeById(id);
        if(employee == null){
            dto = new DetailDto<Employee>().builder().code(404).message("找不到这个员工信息!").build();
        }else{
            dto = new DetailDto<Employee>().builder().code(200)
                                        .message("获取成功")
                                        .title("employee")
                                        .dataTitle(TableTitle.EMPLOYEE_TITLE)
                                        .data(employee)
                                        .addDataMap("deptData",beanDataCache.getDeptMap())
                                        .addDataMap("jobData",beanDataCache.getJobMap())
                                        .addDataMap("sexData",TableTitle.SEX_MAP)
                                        .build();
        }
        log.info(dto);
        return dto;
    }

    @PutMapping("/{id}")
    public SimpleDto update(@PathVariable("id") int id,@RequestParam Map<String,String> param){
        Employee employee = new Employee(param);
        employee.setId(null);

        Set<ConstraintViolation<Employee>> set = validator.validate(employee);

        if(set.size() == 0){
            employee.setId(id);
            hrmService.modifyEmployee(employee);;
            return new SimpleDto(200,"修改成功");
        }else{
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Employee> constraintViolation : set){
                error.put(constraintViolation.getPropertyPath().toString()
                , constraintViolation.getMessage());
            }
            return new SimpleDto(404,error);
        }
    }

    @DeleteMapping("/{id}")
    public SimpleDto delete(@PathVariable("id") int id){
        Employee employee = hrmService.findEmployeeById(id);
        if(employee == null){
            return new SimpleDto(404,"错误的员工序号");
        }else{
            hrmService.removeEmployeeById(id);
            return new SimpleDto(200,"删除成功");
        }
    }


}