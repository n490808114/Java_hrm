package xyz.n490808114.train.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.n490808114.train.domain.Notice;
import xyz.n490808114.train.domain.User;
import xyz.n490808114.train.service.HrmService;
import xyz.n490808114.train.util.RESTfulDataCreate;
import xyz.n490808114.train.util.TrainConstants;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class UserController {
    @Autowired
    @Qualifier("hrmServiceImpl")
    private HrmService hrmService;

    private final static Log log = LogFactory.getLog(UserController.class);

    @GetMapping
    public String login(){
        return "login.html";
    }

    @PostMapping
    @ResponseBody
    public Map<String,Object> login(@RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "loginname", required = false) String loginName,
            @RequestParam("password") String password, HttpSession session) {
        User user = null;
        if(email == null){
            user = hrmService.login(loginName,password);
        }else if(loginName == null){
            user = hrmService.loginByEmail(email,password);
        }
        if(user == null){return RESTfulDataCreate.create(200,"ok",false);}
        session.setAttribute(TrainConstants.USER_SESSION, user);
        return RESTfulDataCreate.create(200,"ok",true);
    }

    @GetMapping("/register")
    public String register(){return "register.html";}

    @PostMapping("/register")
    @ResponseBody
    public boolean register(@RequestParam Map<String,String> param) {
        User user = new User();

        log.info(param);

        user.setUserName(param.getOrDefault("username", TrainConstants.USER_DEFAULT_NAME));

        if(param.containsKey("email")
                && (hrmService.registerCheckEmail(param.get("email")) == 0)){
            user.setEmail(param.get("email"));
        }

        if(param.containsKey("loginname")
                && (hrmService.registerCheckName(param.get("name")) == 0)){
            user.setLoginName(param.get("loginname"));
        }else{return false;}

        if(param.containsKey("password")){
            user.setPassword(param.get("password"));
        }else{return false;}

        user.setCreateDate(new Date());

        hrmService.register(user);

        return true;
    }
}
