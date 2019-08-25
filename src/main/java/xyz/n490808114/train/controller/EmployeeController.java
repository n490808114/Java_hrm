package xyz.n490808114.train.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.ValueFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import xyz.n490808114.train.domain.*;
import xyz.n490808114.train.service.HrmService;
import xyz.n490808114.train.util.BeanDataCache;
import xyz.n490808114.train.util.TableTitle;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private static Log log = LogFactory.getLog(EmployeeController.class);
    @Autowired
    @Qualifier("hrmServiceImpl")
    HrmService hrmService;

    @Autowired
    BeanDataCache beanDataCache;

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @GetMapping
    public String getList(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
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

        if(data.size() == 0){
            map.put("code",404);
            map.put("message","找不到任何的员工");
            return JSON.toJSONString(map);
        }
        map.put("code",200);
        map.put("message","获取成功");
        map.put("count", hrmService.getEmployeeCount(requestParam));
        map.put("dataTitle", TableTitle.employeeListTitle());
        map.put("data",data);

        ValueFilter filter = (Object object, String name, Object value) -> {
            if ("dept".equals(name)) {
                try {
                    return ((Dept) value).getName();
                } catch (ClassCastException ex) {
                    return value;
                }
            } else if ("job".equals(name)) {
                try {
                    return ((Job) value).getName();
                } catch (ClassCastException ex) {
                    return value;
                }
            }
            return value;
        };
        return JSON.toJSONString(map,filter);
    }

    @GetMapping("/create")
    public Map<String, Object> create(){
        Map<String,Object> map = new HashMap<>();
        map.put("title","employee");
        map.put("dataTitle",TableTitle.employeeCreateTitle());
        map.put("deptData", beanDataCache.getDeptMap());
        map.put("jobData",beanDataCache.getJobMap());
        map.put("sexData", TableTitle.sexMap());
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
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Employee> constraintViolation : set){
                error.put(constraintViolation.getPropertyPath().toString()
                , constraintViolation.getMessage());
            }
            map.put("code", 404);
            map.put("message",error);
        }
        return map;
    }

    @GetMapping("/{id}")
    public String getDetail(@PathVariable("id") int id){
        Map<String,Object> map = new HashMap<>();
        map.put("title","employee");
        Employee employee = hrmService.findEmployeeById(id);
        if(employee == null){
            map.put("code",404);
            map.put("message","找不到这个员工");
            return JSON.toJSONString(map);
        }else{
            map.put("code",200);
            map.put("message","获取成功");
            map.put("dataTitle",TableTitle.employeeTitle());
            map.put("data",employee);
            map.put("deptData", beanDataCache.getDeptMap());
            map.put("jobData",beanDataCache.getJobMap());
            map.put("sexData", TableTitle.sexMap());
            ValueFilter valueFilter = (Object object, String name, Object value) -> {
                if ("dept".equals(name)) {
                    try {
                        return ((Dept) value).getName();
                    } catch (ClassCastException ex) {
                        return value;
                    }
                } else if ("job".equals(name)) {
                    try {
                        return ((Job) value).getName();
                    } catch (ClassCastException ex) {
                        return value;
                    }
                }else if("birthday".equals(name)){
                    try{
                        if(value == null){throw new ClassCastException();}
                        return new SimpleDateFormat("YYYY-MM-dd").format((Date) value);
                    }catch(ClassCastException ex){
                        return value;
                    }
                }else if("sex".equals(name)){
                    if(value instanceof Integer){
                        return TableTitle.sexMap().get(value);
                    }
                }
                return value;
            };
            PropertyFilter propertyFilter = (Object Object,String name,Object value) ->{
                if("createDate".equals(name)){
                    return false;
                }
                return true;
            };
            SerializeFilter[] list = new SerializeFilter[2];
            list[0] = valueFilter;
            list[1] = propertyFilter;
            return JSON.toJSONString(map,list);
        }
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
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Employee> constraintViolation : set){
                error.put(constraintViolation.getPropertyPath().toString()
                , constraintViolation.getMessage());
            }
            map.put("code", 404);
            map.put("message",error);
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