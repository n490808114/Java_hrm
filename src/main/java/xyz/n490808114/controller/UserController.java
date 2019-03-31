package xyz.n490808114.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import xyz.n490808114.domain.Notice;
import xyz.n490808114.domain.User;
import xyz.n490808114.service.HrmService;
import xyz.n490808114.util.HrmConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;


@Controller
public class UserController  {
    @Autowired
    @Qualifier("hrmServiceImpl")
    private HrmService hrmService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "redirect:loginForm.html";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(value = "email",required = false ) String email,
                              @RequestParam(value = "loginname",required = false) String loginName,
                              @RequestParam("password") String password,
                              HttpSession session,
                              ModelAndView mv){

        User user = hrmService.login(loginName,password);
        if(user != null){
            session.setAttribute(HrmConstants.USER_SESSION,user);
            mv.setViewName("redirect:main.html");
        }else{
            mv.addObject("message","登录名或密码错误，请重新输入！");
            mv.setViewName("redirect:loginForm.html");
        }
        return mv;
    }
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(){
        return "redirect:registerForm.html";
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@RequestBody User user){
        return null;
    }
}
