package xyz.n490808114.train.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.n490808114.train.dao.UserDao;
import xyz.n490808114.train.domain.User;
import xyz.n490808114.train.util.TrainConstants;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    @Autowired
    private UserDao userDao;
    private static final Log log = LogFactory.getLog(TokenService.class);

    public String loginIn(String username,String password){
        User user = userDao.findByUsernameAndPassword(username,password);
        if(user == null){return null;}
        return getToken(user);
    }
    private SecretKey getSecretKey(){
        //秘钥字段从常量类中获得
        String stringKey = TrainConstants.JWT_SECRET;
        byte[] encodeKey = Base64.decodeBase64(stringKey);
        return new SecretKeySpec(encodeKey,0,encodeKey.length,"AES");
    }

    public User getUserFromToken(String authToken) {
        if(authToken == null || authToken.equals("")) {return  null;}
        SecretKey secretKey = getSecretKey();
        Claims claims = null;
        try{
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken).getBody();
        }catch (ExpiredJwtException ex){return null;}//如果抛出过期Exception,返回null

        return userDao.findById((Integer)claims.get("id"));
    }

    public void loginOut(String token) {

    }
    public String register(Map<String,String> param)throws Exception{
        User user = new User();
        user.setUsername(param.get("username"));
        user.setEmail(param.get("email"));
        user.setPassword(param.get("password"));
        User existedUser = userDao.findByUsername(param.get("username"));
        if(existedUser != null){
            throw new Exception("重复的用户名");
        }
        userDao.insert(user);
        log.info("插入之后获得的UserId: "+user.getId());
        return getToken(user);
    }
    private String getToken(User user){
        //指定签名算法
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        //生成签名的秘钥
        SecretKey secretKey =getSecretKey();
        
        
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 3600000L;
        Date start = new Date(startTime);
        Date end = new Date(endTime);
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",user.getId());
        claims.put("username",user.getUsername());
        String[] roles = user.getRoles().split("#");
        int maxLevel = 0;
        String resultRole = "无任何权限";
        for(String s : roles){
            if(TrainConstants.ROLE_LEVELS.get(s) > maxLevel){
                resultRole = s;
                maxLevel = TrainConstants.ROLE_LEVELS.get(s);
            }
        }
        claims.put("roles", resultRole.split("_")[1]);
        
        return Jwts.builder()
                        .setId(""+user.getId())          // JWT_ID
                        .setAudience("")                 // 接受者
                        .setClaims(claims)               // 自定义属性
                        .setSubject("")                  // 主题
                        .setIssuer("JAVA_HRM")           // 签发者
                        .setIssuedAt(start)              // 签发时间
                        .setNotBefore(start)             // 生效时间
                        .setExpiration(end)              // 过期时间
                        .signWith(algorithm, secretKey)  // 签名算法以及密匙
                        .compact();
    }
}
