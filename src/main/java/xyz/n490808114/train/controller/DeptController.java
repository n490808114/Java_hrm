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

import xyz.n490808114.train.domain.Dept;
import xyz.n490808114.train.service.*;
import xyz.n490808114.train.util.TableTitle;
import xyz.n490808114.train.util.TrainConstants;
import xyz.n490808114.train.service.HrmService;


@RestController
@RequestMapping("/dept")
public class DeptController{
    private static Log log = LogFactory.getLog(DeptController.class);
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
        map.put("title","dept");
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        map.put("code",200);
        map.put("message","获取成功");
        map.put("dataTitle",TableTitle.deptListTitle());
        Map<String,Dept> cacheMap = beanDataCache.getDeptMap();
        List<Dept> data = new LinkedList<>();
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
        map.put("title","dept");
        map.put("dataTitle",TableTitle.deptCreateTitle());
        log.info(map);
        return map;
    }
    @GetMapping("/search")
    public Map<String,Object> search(){
        Map<String,Object> map = new HashMap<>();
        map.put("title","dept");
        map.put("dataTitle",TableTitle.deptSearchTitle());
        log.info(map);
        return map;
    }
    @GetMapping("/{id}")
    public String getDetail(@PathVariable("id") int id){
        Map<String,Object> map = new HashMap<>();
        map.put("title","dept");

        Dept dept = hrmService.findDeptById(id);
        if(dept == null){
            map.put("code",404);
            map.put("message","找不到这个部门");
            return JSON.toJSONString(map);
        }else{
            map.put("code",200);
            map.put("message","获取成功");
            map.put("dataTitle",TableTitle.deptTitle());
            map.put("data",dept);
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
        Dept dept = new Dept();
        dept.setName(param.get("name"));
        dept.setRemark(param.get("remark"));

        Set<ConstraintViolation<Dept>> set = validator.validate(dept);
        Map<String,Object> map = new HashMap<>();
        if(set.size() == 0){
            hrmService.addDept(dept);
            map.put("code",200);
            map.put("message","创建成功");
        }else{
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Dept> constraintViolation : set){
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
        Dept dept = new Dept();
        dept.setName(param.get("name"));
        dept.setRemark(param.get("remark"));

        Set<ConstraintViolation<Dept>> set = validator.validate(dept);
        Map<String,Object> map = new HashMap<>();
        if(set.size() == 0){
            dept.setId(id);
            hrmService.modifyDept(dept);;
            map.put("code",200);
            map.put("message","修改成功");
        }else{
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Dept> constraintViolation : set){
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
        if(beanDataCache.getDeptMap().get(""+id) == null){
            map.put("code","404");
            map.put("message","错误的部门序号");
        }else if(hrmService.countEmployeesByDeptId(id) > 0){
            map.put("code","404");
            map.put("message","该部门内还有员工,请先调整他们的部门后再进行删除");
        }else{
            hrmService.removeDeptById(id);
            map.put("code","200");
            map.put("message","删除成功");
        }
        log.info(map);
        return map;
    }

}