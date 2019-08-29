package xyz.n490808114.train.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import xyz.n490808114.train.domain.Notice;
import xyz.n490808114.train.util.TableTitle;
import xyz.n490808114.train.domain.User;
import xyz.n490808114.train.dto.DetailDto;
import xyz.n490808114.train.dto.ListDto;
import xyz.n490808114.train.dto.SimpleDto;
import xyz.n490808114.train.service.HrmService;
import xyz.n490808114.train.util.TrainConstants;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpSession;

import java.util.*;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    @Qualifier("hrmServiceImpl")
    private HrmService hrmService;
    private static Log log = LogFactory.getLog(NoticeController.class);
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @GetMapping
    public ListDto<Notice> getList(@RequestParam(value = "pageSize",defaultValue = "20") int pageSize,
                        @RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                        @RequestParam Map<String,String> requestParam)
    {

        Map<String, Object> param = new HashMap<>(requestParam);
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);

        List<Notice> data = hrmService.getNoticeList(param);
        ListDto<Notice> dto = null;
        if(data.size() == 0){
            dto = new ListDto<Notice>(404,"找不到任何的公告");
        }
        dto =  new ListDto<Notice>(200,
                                "获取成功",
                                "notice",
                                pageNo,
                                pageSize,
                                hrmService.getNoticesCount(requestParam),
                                TableTitle.NOTICE_LIST_TITLE,
                                data);
        log.info(dto);
        return dto;
    }

    /**
     * 新建Notice可供前台填写的内容
     * @return 可填写的内容String
     */
    @GetMapping("/create")
    public Map<String, Object> create() {
        Map<String,Object> map = new HashMap<>();
        map.put("title","notice");
        map.put("dataTitle",TableTitle.NOTICE_CREATE_TITLE);
        return map;
    }
    @GetMapping("/search")
    public Map<String,Object> search(){
        Map<String,Object> map = new HashMap<>();
        map.put("title","notice");
        map.put("dataTitle",TableTitle.NOTICE_SEARCH_TITLE);
        return map;
    }

    /**
     * 收到前台填写的内容，并获取客户的User,使用当前日期，Mysql中id是自动递增的,所以不指定id
     * 

     * @param session 会话，从中获取客户的User
     * @return 标题不为空，返回true,否则返回false
     */
    @PostMapping
    public SimpleDto create(@RequestParam Map<String, String> param, HttpSession session) {
        Notice notice = new Notice();
        notice.setTitle(param.get("title"));
        notice.setContent(param.get("content"));
        notice.setCreateDate(new Date());
        notice.setUser((User) session.getAttribute(TrainConstants.USER_SESSION));

        Set<ConstraintViolation<Notice>> set = validator.validate(notice);
        if(set.size() == 0){
            hrmService.addNotice(notice);
            return new SimpleDto(200,"创建成功");
        }else{
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Notice> constraintViolation : set){
                error.put(constraintViolation.getPropertyPath().toString()
                , constraintViolation.getMessage());
            }
            return new SimpleDto(404,error);
        }
    }

    /**
     * 获取指定id 的 Notice 的全部内容
     * 
     * @param id 指定id
     * @return 指定id 的 Notice 转换为JSON字符串
     */
    @GetMapping("/{id}")
    public DetailDto getDetail(@PathVariable("id") int id) {
        DetailDto dto = null;
        Notice  notice = hrmService.findNoticeById(id);
        if(notice == null){
            dto = new DetailDto().builder().code(404).message("找不到这个公告").build();
        }else{
            dto = new DetailDto().builder()
                            .code(200)
                            .message("获取成功")
                            .title("notice")
                            .dataTitle(TableTitle.NOTICE_TITLE)
                            .data(notice)
                            .build();
        }
        log.info(dto);
        return dto;
    }

    /**
     * 获取在详情Detail页修改后的所有内容，使用其中的id,title,content;使用当天的new
     * Date();使用会话session中的用户User
     *
     * @param session 会话session,用于获取User
     * @return 标题不为空，返回true,否则返回false
     */
    @PutMapping("/{id}")
    public SimpleDto update(@PathVariable("id") int id, @RequestParam Map<String, String> param, HttpSession session) {
        Notice notice = new Notice();
        notice.setTitle(param.get("title"));
        notice.setContent(param.get("content"));

        Set<ConstraintViolation<Notice>> set = validator.validate(notice);
        if(set.size() == 0){
            notice.setId(id);
            hrmService.modifyNotice(notice);;
            return new SimpleDto(200,"创建成功");
        }else{
            Map<String,String> error = new LinkedHashMap<>();
            for(ConstraintViolation<Notice> constraintViolation : set){
                error.put(constraintViolation.getPropertyPath().toString()
                , constraintViolation.getMessage());
            }
            return  new SimpleDto(404,error);
        }
    }

    /**
     * 获取在详情Detail页修改后的所有内容，使用其中的id去删除该项
     * @return 提交删除指定,返回给前台true
     */
    @DeleteMapping("/{id}")
    public SimpleDto delete(@PathVariable("id") int id) {
        Notice notice = hrmService.findNoticeById(id);
        if(notice == null){
            return new SimpleDto(404,"错误的公告序号");
        }else{
            hrmService.removeNotice(id);
            return new SimpleDto(200,"删除成功");
        }
    }
}
