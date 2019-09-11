package xyz.n490808114.train.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    //根目录访问返回静态文件main.html
    @RequestMapping("/")
    public String getMainPage(){
        return "main.html";
    }
}
