package xyz.n490808114.train.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xyz.n490808114.train.domain.User;
import xyz.n490808114.train.service.TokenService;
import xyz.n490808114.train.util.TrainConstants;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
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
    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> register(@RequestParam Map<String,String> param){

        Map<String,String> map = checkRegisterParam(param);
        Map<String,Object> r = new HashMap<>();
        if(!"".equals(map.get("message"))){
            r.put("message",map.get("message"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        try{
            r.put("token",tokenService.register(param));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(r);
        }catch(Exception ex){
            r.put("message",ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        
    }
    public Map<String,String> checkRegisterParam(Map<String,String> param){
        String username = param.get("username");
        String email = param.get("email");
        String password = param.get("password");
        String result = "";
        if("".equals(username) || username == null){
            result += "用户名为空;\n";
        }
        if(email != null && !"".equals(email) && email.split("@").length != 2){
            result += "email格式错误;\n";
        }
        if("".equals(password) || password == null){
            result += "密码为空;\n";
        }
        param.put("message", result);
        return param;
    }
    @RequestMapping("user/{id}/name")
    public ResponseEntity<String> getMyselvesName(@PathVariable int id,HttpServletRequest request){
        User user =(User) request.getAttribute(TrainConstants.USER_REQUEST);
        if(user == null || id != user.getId()){
            return new ResponseEntity<String>("null", HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<String>(user.getName(), HttpStatus.OK);
        }
    }
}
