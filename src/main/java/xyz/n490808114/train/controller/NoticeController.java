package xyz.n490808114.train.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import xyz.n490808114.train.domain.Notice;
import xyz.n490808114.train.util.TableTitle;
import xyz.n490808114.train.domain.User;
import xyz.n490808114.train.service.HrmService;
import xyz.n490808114.train.util.TrainConstants;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    @Qualifier("hrmServiceImpl")
    private HrmService hrmService;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @GetMapping
    public String getList(@RequestParam(value = "pageSize",defaultValue = "20") int pageSize,
                        @RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                        @RequestParam Map<String,String> requestParam)
    {
        Map<String, Object> param = new HashMap<>(requestParam);
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);

        List<Notice> data = hrmService.getNoticeList(param);

        Map<String, Object> json = new HashMap<>();
        json.put("title","notice");
        json.put("pageSize", pageSize);
        json.put("pageNo", pageNo);

        if(data.size() == 0){
            json.put("code",404);
            json.put("message","找不到任何的公告");
            return JSON.toJSONString(json);
        }
        json.put("code",200);
        json.put("message","获取成功");
        json.put("count", hrmService.getNoticesCount(requestParam));
        json.put("dataTitle", TableTitle.noticeListTitle());
        json.put("data", data);

        ValueFilter filter = (Object object, String name, Object value) -> {
            if ("user".equals(name)) {
                try {
                    return value == null?"None":((User) value).getUserName();
                } catch (ClassCastException ex) {
                    return value;
                }
            }else if("createDate".equals(name)){
                try{
                    if(value == null){throw new ClassCastException();}
                    return new SimpleDateFormat("YYYY-MM-dd").format((Date) value);
                }catch(ClassCastException ex){
                    return value;
                }
            }
            return value;
        };
        return JSON.toJSONString(json,filter);
    }

    /**
     * 新建Notice可供前台填写的内容
     * @return 可填写的内容String
     */
    @GetMapping("/create")
    public Map<String, Object> create() {
        Map<String,Object> map = new HashMap<>();
        map.put("title","notice");
        map.put("dataTitle",TableTitle.noticeCreateTitle());
        return map;
    }
    @GetMapping("/search")
    public Map<String,Object> search(){
        Map<String,Object> map = new HashMap<>();
        map.put("title","notice");
        map.put("dataTitle",TableTitle.noticeSearchTitle());
        return map;
    }

    /**
     * 收到前台填写的内容，并获取客户的User,使用当前日期，Mysql中id是自动递增的,所以不指定id
     * 

     * @param session 会话，从中获取客户的User
     * @return 标题不为空，返回true,否则返回false
     */
    @PostMapping
    public Map<String,Object> create(@RequestParam Map<String,String> param,HttpSession session) {
        Notice notice = new Notice();
        notice.setTitle(param.get("title"));
        notice.setContent(param.get("content"));
        notice.setCreateDate(new Date());
        notice.setUser((User) session.getAttribute(TrainConstants.USER_SESSION));

        Set<ConstraintViolation<Notice>> set = validator.validate(notice);
        Map<String,Object> map = new HashMap<>();
        if(set.size() == 0){
            hrmService.addNotice(notice);
            map.put("code",200);
            map.put("message","创建成功");
        }else{
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Notice> constraintViolation : set){
                error.put(constraintViolation.getPropertyPath().toString()
                , constraintViolation.getMessage());
            }
            map.put("code", 404);
            map.put("message",error);
        }
        return map;
    }

    /**
     * 获取指定id 的 Notice 的全部内容
     * 
     * @param id 指定id
     * @return 指定id 的 Notice 转换为JSON字符串
     */
    @GetMapping("/{id}")
    public String getDetail(@PathVariable("id") int id) {
        Map<String,Object> map = new HashMap<>();
        map.put("title","notice");
        Notice notice = hrmService.findNoticeById(id);
        if(notice == null){
            map.put("code",404);
            map.put("message","找不到这个公告");
            return JSON.toJSONString(map);
        }else{
            map.put("code",200);
            map.put("message","获取成功");
            map.put("dataTitle",TableTitle.noticeTitle());
            map.put("data",notice);
            ValueFilter valueFilter = (Object object, String name, Object value) -> {
                if ("user".equals(name)) {
                    try {
                        return ((User) value).getUserName();
                    } catch (ClassCastException ex) {
                        return value;
                    }
                }
                return value;
            };
            PropertyFilter propertyFilter = (Object Object,String name,Object value) ->{
                return !name.equals("createDate");
            };
            SerializeFilter[] list = new SerializeFilter[2];
            list[0] = valueFilter;
            list[1] = propertyFilter;
            return JSON.toJSONString(map,list);
        }

    }

    /**
     * 获取在详情Detail页修改后的所有内容，使用其中的id,title,content;使用当天的new
     * Date();使用会话session中的用户User
     *
     * @param session 会话session,用于获取User
     * @return 标题不为空，返回true,否则返回false
     */
    @PutMapping("/{id}")
    public Map<String,Object> update(@PathVariable("id") int id,@RequestParam Map<String, String> param, HttpSession session) {
        Notice notice = new Notice();
        notice.setTitle(param.get("title"));
        notice.setContent(param.get("content"));

        Set<ConstraintViolation<Notice>> set = validator.validate(notice);
        Map<String,Object> map = new HashMap<>();
        if(set.size() == 0){
            notice.setId(id);
            hrmService.modifyNotice(notice);;
            map.put("code",200);
            map.put("message","创建成功");
        }else{
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Notice> constraintViolation : set){
                error.put(constraintViolation.getPropertyPath().toString()
                , constraintViolation.getMessage());
            }
            map.put("code", 404);
            map.put("message",error);
        }
        return map;
    }

    /**
     * 获取在详情Detail页修改后的所有内容，使用其中的id去删除该项
     * @return 提交删除指定,返回给前台true
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") int id) {
        Notice notice = hrmService.findNoticeById(id);
        Map<String, Object> map = new HashMap<>();
        if(notice == null){
            map.put("code","404");
            map.put("message","错误的公告序号");
        }else{
            hrmService.removeNotice(id);
            map.put("code","200");
            map.put("message","删除成功");
        }
        return map;
    }
}
