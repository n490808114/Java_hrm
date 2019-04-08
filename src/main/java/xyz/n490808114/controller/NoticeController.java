package xyz.n490808114.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.n490808114.domain.Notice;
import xyz.n490808114.domain.TableTitle;
import xyz.n490808114.domain.User;
import xyz.n490808114.service.HrmService;
import xyz.n490808114.util.HrmConstants;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
public class NoticeController {
    @Autowired
    @Qualifier("hrmServiceImpl")
    private HrmService hrmService;

    @ResponseBody
    @RequestMapping(value = "/notice",produces = "application/json;charset=utf-8")
    public String getLast10Notices(){
        Notice notice1 = hrmService.findNoticeById(1);
        Notice notice2 = hrmService.findNoticeById(2);
        Notice notice3 = hrmService.findNoticeById(3);

        List<Object> notices = new ArrayList<>();
        notices.add(TableTitle.NoticeTile());
        notices.add(notice1);
        notices.add(notice2);
        notices.add(notice3);

        ValueFilter filter = (Object o, String s, Object o1)->{
            if("user".equals(s)){
                try{
                    return ((User) o1).getUserName();
                }catch(ClassCastException ex){
                    return o1;
                }
            }
            return o1;
        };

        return JSON.toJSONString(notices,filter);
    }

    @RequestMapping(value = "/noticeCreate",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String noticeCreate(){
        Map<String,String> map = new HashMap<>();
        map.put("title","标题");
        map.put("content","公告内容");
        return JSON.toJSONString(map);
    }
    @RequestMapping(value = "/noticeCreate",method = RequestMethod.POST)
    @ResponseBody
    public String noticeCreate(@RequestParam("content") String content,
                               @RequestParam("title") String title,
                               HttpSession session){
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setCreateDate(new Date());
        notice.setUser((User) session.getAttribute(HrmConstants.USER_SESSION));
        if(!"".equals(title.trim())) {
            hrmService.addNotice(notice);
            return JSON.toJSONString(true);
        }else {
            return JSON.toJSONString(false);
        }
    }

}
