package xyz.n490808114.train.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.n490808114.train.domain.Notice;
import xyz.n490808114.train.util.TableTitle;
import xyz.n490808114.train.domain.User;
import xyz.n490808114.train.service.HrmService;
import xyz.n490808114.train.util.TrainConstants;


import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    @Qualifier("hrmServiceImpl")
    private HrmService hrmService;

    @GetMapping
    public String getList(@RequestParam(value = "pageSize",defaultValue = "20") int pageSize,
                          @RequestParam(value = "pageNo",defaultValue = "1") int pageNo)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);

        List<Notice> data = hrmService.getNoticeList(param);

        Map<String, Object> json = new HashMap<>();
        json.put("count", hrmService.getNoticesCount());
        json.put("pageSize", pageSize);
        json.put("pageNo", pageNo);
        json.put("title", TableTitle.noticeListTitle());
        json.put("data", data);

        ValueFilter filter = (Object object, String name, Object value) -> {
            if ("user".equals(name)) {
                try {
                    return value == null?"None":((User) value).getUserName();
                } catch (ClassCastException ex) {
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
    public Map<String, String> create() {
        return TableTitle.noticeCreateTitle();
    }

    /**
     * 收到前台填写的内容，并获取客户的User,使用当前日期，Mysql中id是自动递增的,所以不指定id
     * 
     * @param content 获取到的文本
     * @param title   获取到的标题
     * @param session 会话，从中获取客户的User
     * @return 标题不为空，返回true,否则返回false
     */
    @PostMapping
    public boolean create(@RequestParam("content") String content, @RequestParam("title") String title,
            HttpSession session) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setCreateDate(new Date());
        notice.setUser((User) session.getAttribute(TrainConstants.USER_SESSION));
        if (!"".equals(title.trim())) {
            hrmService.addNotice(notice);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取指定id 的 Notice 的全部内容
     * 
     * @param id 指定id
     * @return 指定id 的 Notice 转换为JSON字符串
     */
    @GetMapping("/{id}")
    public Map<String,Object> getDetail(@PathVariable("id") String id) {
        Map<String,Object> map = new HashMap<>();
        map.put("title",TableTitle.noticeTitle());
        map.put("data",hrmService.findNoticeById(Integer.parseInt(id)));
/*        ValueFilter filter = (Object object, String name, Object value) -> {
            if ("user".equals(name)) {
                try {
                    return ((User) value).getUserName();
                } catch (ClassCastException ex) {
                    return value;
                }
            }else if("createDate".equals(name)){
                try{
                    return new SimpleDateFormat("YYYY-MM-dd").format((Date) value);
                }catch(ClassCastException ex){
                    return value;
                }
            }
            return value;
        };*/
        return map;
    }

    /**
     * 获取在详情Detail页修改后的所有内容，使用其中的id,title,content;使用当天的new
     * Date();使用会话session中的用户User
     * 
     * @param map     详情Detail页修改后的所有内容
     * @param session 会话session,用于获取User
     * @return 标题不为空，返回true,否则返回false
     */
    @PutMapping("/{id}")
    public boolean update(@PathVariable("id") int id,@RequestParam Map<String, String> map, HttpSession session) {
        Notice notice = new Notice();
        notice.setId(Integer.parseInt(map.get("id")));
        notice.setTitle(map.get("title"));
        notice.setContent(map.get("content"));
        notice.setCreateDate(new Date());
        notice.setUser((User) session.getAttribute(TrainConstants.USER_SESSION));
        if (!"".equals(notice.getTitle().trim())) {
            hrmService.modifyNotice(notice);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取在详情Detail页修改后的所有内容，使用其中的id去删除该项
     * @return 提交删除指定,返回给前台true
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id) {
        hrmService.removeNotice(id);
        return true;
    }
}
