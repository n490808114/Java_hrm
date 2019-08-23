package xyz.n490808114.train.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import xyz.n490808114.train.domain.*;
import xyz.n490808114.train.service.HrmService;
import xyz.n490808114.train.util.BeanDataCache;
import xyz.n490808114.train.util.TableTitle;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    @Qualifier("hrmServiceImpl")
    HrmService hrmService;

    @Autowired
    BeanDataCache beanDataCache;




    @GetMapping
    public String getList(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                                    @RequestParam(value = "pageSize",defaultValue = "20") int pageSize) {

        Map<String, Object> param = new HashMap<>();
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);

        List<Employee> data =  hrmService.getEmployeeList(param);

        Map<String, Object> json = new HashMap<>();
        json.put("title","employee");
        json.put("pageNo", pageNo);
        json.put("pageSize", pageSize);

        if(data.size() == 0){
            json.put("code",404);
            json.put("message","找不到任何的员工");
            return JSON.toJSONString(json);
        }
        json.put("code",200);
        json.put("message","获取成功");
        json.put("count", hrmService.getEmployeeCount());
        json.put("dataTitle", TableTitle.employeeListTitle());
        json.put("data",data);

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
        return JSON.toJSONString(json,filter);
    }

    @GetMapping("/create")
    public Map<String, Object> create(){
        Map<String, Object> json = new LinkedHashMap<>(TableTitle.employeeCreateTitle());
        json.put("sexData", TableTitle.sexMap());
        json.put("deptData",beanDataCache.getDeptMap());
        json.put("jobData",beanDataCache.getJobMap());
        return json;
    }
    @PostMapping
    public Map<String,Object> create(@RequestParam Map<String,String> map){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Employee employee = new Employee(map);
        hrmService.addEmployee(map);
        Map<String,Object> json = new HashMap<>();
        json.put("code",200);
        json.put("message","创建成功");
        return json;
    }


    @GetMapping("/{id}")
    public String getDetail(@PathVariable("id") String id){
        Map<String,Object> map = new HashMap<>();
        map.put("dataTitle",TableTitle.employeeTitle());
        map.put("data",hrmService.findEmployeeById(Integer.parseInt(id)));
        map.put("deptData", beanDataCache.getDeptMap());
        map.put("jobData",beanDataCache.getJobMap());
        map.put("sexData", TableTitle.sexMap());

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
            }else if("createDate".equals(name) || "birthday".equals(name)){
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
        return JSON.toJSONString(map,filter);
    }





    @PutMapping("/{id}")
    public String update(@PathVariable("id") int id,@RequestParam Map<String,String> map){
        if(map.get("name") == null){return "false";}
        hrmService.modifyEmployee(map);
        return "true";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        hrmService.removeEmployeeById(id);
        return "true";
    }


}