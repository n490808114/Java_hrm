package xyz.n490808114.train.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.n490808114.train.domain.User;
import xyz.n490808114.train.service.HrmService;
import xyz.n490808114.train.util.RESTCreater;
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
        if(user == null){return RESTCreater.create(200,"登录失败，请检查您的账号密码信息是否正确，请重新登录",false);}
        session.setAttribute(TrainConstants.USER_SESSION, user);
        return RESTCreater.create(200,"登陆成功",true);
    }

    @GetMapping("/register")
    public String register(){return "register.html";}

    @PostMapping("/register")
    @ResponseBody
    public Map<String,Object> register(@RequestParam Map<String,String> param) {
        User user = new User();

        log.info(param);
        StringBuilder sb = new StringBuilder();
        user.setUserName(param.getOrDefault("username", TrainConstants.USER_DEFAULT_NAME));

        if(param.containsKey("email") && emailCheck(param.get("email"))){
            user.setEmail(param.get("email"));
        }else{sb.append("邮箱重复或格式错误，请修改邮箱\n");}

        if(param.containsKey("loginname") && loginNameCheck(param.get("loginname"))){
            user.setLoginName(param.get("loginname"));
        }else{sb.append("登录名重复或格式错误，请修改登录名\n");}

        if(param.containsKey("password") && passwordCheck(param.get("password"))){
            user.setPassword(param.get("password"));
        }else{sb.append("密码格式错误，请修改密码\n");}

        user.setCreateDate(new Date());
        if(sb.length() == 0){
            hrmService.register(user);
            return RESTCreater.create(200,"注册成功，即将跳转登录",true);
        }else{
            return RESTCreater.create(200,sb.toString(),false);
        }

    }
    private boolean loginNameCheck(String name){
        return (hrmService.registerCheckName(name) == 0);
    }
    private boolean emailCheck(String email){
        return (hrmService.registerCheckEmail(email) == 0);
    }
    private boolean passwordCheck(String password){
        return password.length() >= 8;
    }
}
