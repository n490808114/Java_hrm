package xyz.n490808114.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping({ "/index", "/" })
    public String index() {
        return "redirect:main.html";
    }
}
