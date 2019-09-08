package xyz.n490808114.train.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

    public String loginIn(String username,String password){
        User user = userDao.findByUsernameAndPassword(username,password);
        if(user == null){return null;}
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

        return Jwts.builder()
                .setId(""+user.getId())                          // JWT_ID
                .setAudience("")                                // 接受者
                .setClaims(claims)                                // 自定义属性
                .setSubject("")                                 // 主题
                .setIssuer("JAVA_HRM")                         // 签发者
                .setIssuedAt(start)                            // 签发时间
                .setNotBefore(start)                        // 失效时间
                .setExpiration(end)                      // 过期时间
                .signWith(algorithm, secretKey)// 签名算法以及密匙
                .compact();
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

}
