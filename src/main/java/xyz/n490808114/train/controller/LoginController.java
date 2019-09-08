package xyz.n490808114.train.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.n490808114.train.service.TokenService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private final static Log log = LogFactory.getLog(LoginController.class);
    @Autowired
    private TokenService tokenService;



    @RequestMapping("/login")
    @PostMapping
    public ResponseEntity<Map<String,Object>> loginIn(@RequestParam Map<String,String> userInfo){
        String username = userInfo.get("username");
        String password = userInfo.get("password");
        HashMap<String,Object> result = new HashMap<>();
        String token = tokenService.loginIn(username,password);
        if(token == null){
            result.put("message","invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }else{
            result.put("token",token);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }
    public ResponseEntity<String> loginOut(HttpServletRequest request){
        String token = null;
        String requestHeader = request.getHeader("Authorization");
        if(requestHeader != null && requestHeader.startsWith("Bearer ")){
            token = requestHeader.substring(7);
            tokenService.loginOut(token);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
