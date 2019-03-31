package xyz.n490808114.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.n490808114.domain.Notice;
import xyz.n490808114.domain.TableTitle;
import xyz.n490808114.domain.User;
import xyz.n490808114.service.HrmService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NoticeController {
    @Autowired
    @Qualifier("hrmServiceImpl")
    private HrmService hrmService;

    @ResponseBody
    @RequestMapping(value = "/notice",produces = "application/json;charset=utf-8")
    public String getNoticesTest(){
        Notice notice1 = hrmService.findNoticeById(1);
        Notice notice2 = hrmService.findNoticeById(2);
        Notice notice3 = hrmService.findNoticeById(3);

        List<Object> notices = new ArrayList<>();
        notices.add(TableTitle.NoticeTile());
        notices.add(notice1);
        notices.add(notice2);
        notices.add(notice3);

        ValueFilter filter = new ValueFilter() {
            @Override
            public Object process(Object o, String s, Object o1) {
                if("user".equals(s)){
                    return ((User) o1).getUserName();
                }
                return o1;
            }
        };
        return JSON.toJSONString(notices,filter);
    }
}
