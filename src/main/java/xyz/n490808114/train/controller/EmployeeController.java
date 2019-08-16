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
import xyz.n490808114.train.util.TableTitle;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    @Qualifier("hrmServiceImpl")
    HrmService hrmService;

    private static Map<String,String> deptMap = new HashMap<>();
    private static Map<String,String> jobMap = new HashMap<>();
    private static Boolean isLoadedMaps = false;

    @GetMapping
    public String getList(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                                    @RequestParam(value = "pageSize",defaultValue = "20") int pageSize) {

        Map<String, Object> param = new HashMap<>();
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);

        Map<String, Object> json = new HashMap<>();
        json.put("title","employee");
        json.put("count", hrmService.getEmployeeCount());
        json.put("pageNo", pageNo);
        json.put("pageSize", pageSize);
        json.put("dataTitle", TableTitle.employeeListTitle());
        json.put("data", hrmService.getEmployeeList(param));

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


    @GetMapping("/{id}")
    public String getDetail(@PathVariable("id") String id){
        load();
        Map<String,Object> map = new HashMap<>();
        map.put("dataTitle",TableTitle.employeeTitle());
        map.put("data",hrmService.findEmployeeById(Integer.parseInt(id)));
        map.put("deptData",deptMap);
        map.put("jobData",jobMap);
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

    @GetMapping("/create")
    public Map<String, Object> create(){
        load();
        Map<String, Object> json = new LinkedHashMap<>();
        json.putAll(TableTitle.employeeCreateTitle());
        json.put("sexData", TableTitle.sexMap());
        json.put("deptData",deptMap);
        json.put("jobData",jobMap);

        PropertyFilter filter = (Object object, String name, Object value) ->{
            if("".equals(value)){
                try{
                    ((Map<String,String>) object).remove(name);
                }catch(ClassCastException ex){
                    return false;
                }
            }
            return true;
        };
        return json;
    }

    @PostMapping
    public String create(@RequestParam Map<String,String> map){
        hrmService.addEmployee(map);
        return "true";
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

    public void load(){
        if(!isLoadedMaps){
            synchronized(isLoadedMaps){
                if(!isLoadedMaps){
                    synchronized(EmployeeController.class){
                        loadMaps();
                    }
                    isLoadedMaps = true;
                }
            }
        }
    }
    public void loadMaps(){
        List<Dept> depts = hrmService.findAllDept();
        for(Dept dept : depts){
            while(true){
                try{
                    deptMap.put(""+dept.getId(), dept.getName());
                    break;
                }catch(Exception ex){}
            }
            
        }
        List<Job> jobs = hrmService.findAllJob();
        for(Job job : jobs){
            while(true){
                try{
                    jobMap.put(""+job.getId(),job.getName());
                    break;
                }catch(Exception ex){}
            }
        }
    }
}