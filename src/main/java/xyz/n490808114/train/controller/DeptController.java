package xyz.n490808114.train.controller;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import xyz.n490808114.train.domain.Dept;
import xyz.n490808114.train.util.BeanDataCache;
import xyz.n490808114.train.util.TableTitle;
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
        Map<String,String> cacheDeptMap = beanDataCache.getDeptMap();
        List<Dept> data = new LinkedList<>();
        int mapCount = 0;
        int start = (pageNo - 1) * pageSize;
        if(requestParam.get("name") != null && !requestParam.get("name").equals("")){
            String name = requestParam.get("name");
            int count = 0;
            for(String key : cacheDeptMap.keySet()){
                if(cacheDeptMap.get(key).contains(name)){
                    if(mapCount == start && data.size() < pageSize){
                        data.add(new Dept(Integer.parseInt(key),cacheDeptMap.get(key)));
                    }else{
                        mapCount++;
                    }
                    count++;
                }
            }
            map.put("count",count);
            map.put("data",data);
        }else{
            for(String key : cacheDeptMap.keySet()){
                if(mapCount == start && data.size() < pageSize){
                    data.add(new Dept(Integer.parseInt(key),cacheDeptMap.get(key)));
                }else if(data.size() == pageSize){
                    break;
                }else{
                    mapCount++;
                }
            }
            map.put("count",cacheDeptMap.size());
            map.put("data",data);
        }
        return JSON.toJSONString(map);
    }
    @GetMapping("/create")
    public Map<String,Object> create(){
        Map<String,Object> map = new HashMap<>();
        map.put("title","dept");
        map.put("dataTitle",TableTitle.deptCreateTitle());
        return map;
    }
    @GetMapping("/search")
    public Map<String,Object> search(){
        Map<String,Object> map = new HashMap<>();
        map.put("title","dept");
        map.put("dataTitle",TableTitle.deptSearchTitle());
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
            return JSON.toJSONString(map,propertyFilter);
        }
    }
    @PutMapping("/{id}")
    public Map<String,Object> update(@PathVariable("id") int id,@RequestParam Map<String,String> param){
        Dept dept = new Dept();
        dept.setId(id);
        dept.setName(param.get("name"));
        dept.setRemark(param.get("remark"));
        hrmService.modifyDept(dept);
        beanDataCache.reLoadDeptMap();
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("message","创建成功");
        log.info(map);
        return map;
    }

}