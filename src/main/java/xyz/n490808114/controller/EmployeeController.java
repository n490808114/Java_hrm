package xyz.n490808114.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import xyz.n490808114.domain.*;
import xyz.n490808114.service.HrmService;

@Controller
class EmployeeController {
    HrmService hrmService;

    private static List<String> deptList = new ArrayList<>(10);
    private static List<String> jobList = new ArrayList<>(10);

    @Autowired
    EmployeeController(@Qualifier("hrmServiceImpl") HrmService hrmService){
        this.hrmService = hrmService;
        
        List<Dept> depts = hrmService.findAllDept();
        for(Dept dept : depts){
            while(true){
                try{
                    deptList.set(dept.getId(), dept.getName());
                    break;
                }catch(Exception ex){
                    deptList.add("");
                }
            }
            
        }
        List<Job> jobs = hrmService.findAllJob();
        for(Job job : jobs){
            while(true){
                try{
                    jobList.set(job.getId(),job.getName());
                    break;
                }catch(Exception ex){
                    deptList.add("");
                }
            }
        }
    }
    @RequestMapping(value = "/employee", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getList(@RequestParam("pageNo") String pageNoString,
            @RequestParam("pageSize") String pageSizeString) {

        Map<String, Object> param = new HashMap<>();
        param.put("pageNo", Integer.parseInt(pageNoString));
        param.put("pageSize", Integer.parseInt(pageSizeString));

        Map<String, Object> json = new HashMap<>();
        json.put("count", hrmService.getEmployeeCount());
        json.put("pageNo", pageNoString);
        json.put("pageSize", pageSizeString);
        json.put("title", TableTitle.employeeListTitle());
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
        return JSON.toJSONString(json, filter);
    }


    @RequestMapping(value = "/employeeDetail",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getDetail(@RequestParam("id") String id){
        List<Object> list = new LinkedList<>();
        list.add(TableTitle.employeeTitle());
        list.add(hrmService.findEmployeeById(Integer.parseInt(id)));
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
                try{
                    int a = Integer.parseInt("" + value);
                    for(Sex s : Sex.values()){
                        if(s.getIndex() == a ){return s.getData();}
                    }
                    return Sex.UNKNOW.getData();
                }catch(NumberFormatException ex){
                    return value;
                }
            }
            return value;
        };
        return JSON.toJSONString(list,filter);
    }

    @RequestMapping(value = "/employeeCreate",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String create(){
        Map<String, Object> json = new LinkedHashMap<>();
        json.putAll(TableTitle.employeeCreateTitle());
        json.put("sexData", TableTitle.sexList());
        json.put("deptData",deptList);
        json.put("jobData",jobList);
        return JSON.toJSONString(json);
    }
    @RequestMapping(value = "/employeeUpdate",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String update(@RequestParam Map<String,String> map){
        if(map.get("name") == null){return "false";}
        hrmService.modifyEmployee(map);
        return "true";
    }

}