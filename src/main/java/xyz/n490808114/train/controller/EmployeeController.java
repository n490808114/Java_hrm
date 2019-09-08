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
import xyz.n490808114.train.service.BeanDataCache;
import xyz.n490808114.train.service.HrmService;
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
    public ListDto<Employee> getList(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                                    @RequestParam(value = "pageSize",defaultValue = "20") int pageSize,
                                    @RequestParam Map<String,String> requestParam) {
        log.info(requestParam);
        Map<String, String> param = new HashMap<>(requestParam);
        param.put("pageNo", ""+pageNo);
        param.put("pageSize", ""+pageSize);

        List<Employee> data =  hrmService.getEmployeeList(param);

        Map<String, Object> map = new HashMap<>();
        map.put("title","employee");
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        
        ListDto<Employee> dto = null;
        if(data.size() == 0){
            dto = new ListDto<>(404,"找不到任何的员工");
        }/*
        dto = new ListDto<>(200,"获取成功","employee",pageNo,pageSize,
                                    hrmService.getEmployeeCount(requestParam),
                                    TableTitle.EMPLOYEE_LIST_TITLE,
                                    data);*/

        return dto;
    }

    @GetMapping("/create")
    public Map<String, Object> create(){
        Map<String,Object> map = new HashMap<>();
        map.put("title","employee");
        map.put("dataTitle",TableTitle.EMPLOYEE_CREATE_TITLE);
        map.put("deptData", beanDataCache.getDeptMap());
        map.put("jobData",beanDataCache.getJobMap());
        map.put("sexData", TableTitle.SEX_MAP);
        return map;
    }
    @GetMapping("/search")
    public Map<String,Object> search(){
        return create();
    }
    @PostMapping
    public Map<String,Object> create(@RequestParam Map<String,String> param){
        Employee employee = new Employee(param);
        Set<ConstraintViolation<Employee>> set = validator.validate(employee);

        Map<String,Object> map = new HashMap<>();
        if(set.size() == 0){
            employee.setCreateDate(new Date());
            hrmService.addEmployee(employee);
            map.put("code",200);
            map.put("message","创建成功");
        }else{
            makeErrorToMap(set, map);
        }
        return map;
    }

    private void makeErrorToMap(Set<ConstraintViolation<Employee>> set, Map<String, Object> map) {
        Map<String,String> error = new LinkedHashMap<>();
        for(ConstraintViolation<Employee> constraintViolation : set){
            error.put(constraintViolation.getPropertyPath().toString()
            , constraintViolation.getMessage());
        }
        map.put("code", 404);
        map.put("message",error);
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
    public Map<String,Object> update(@PathVariable("id") int id,@RequestParam Map<String,String> param){
        Employee employee = new Employee(param);
        employee.setId(null);

        Set<ConstraintViolation<Employee>> set = validator.validate(employee);

        Map<String,Object> map = new HashMap<>();
        if(set.size() == 0){
            employee.setId(id);
            hrmService.modifyEmployee(param);;
            map.put("code",200);
            map.put("message","创建成功");
        }else{
            makeErrorToMap(set, map);
        }
        return map;
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") int id){
        Employee employee = hrmService.findEmployeeById(id);
        Map<String, Object> map = new HashMap<>();
        if(employee == null){
            map.put("code","404");
            map.put("message","错误的员工序号");
        }else{
            hrmService.removeEmployeeById(id);
            map.put("code","200");
            map.put("message","删除成功");
        }
        return map;
    }


}