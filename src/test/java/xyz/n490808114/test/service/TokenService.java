package xyz.n490808114.test.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import org.springframework.test.context.junit4.SpringRunner;
import xyz.n490808114.train.dao.UserDao;
import xyz.n490808114.train.domain.User;
import xyz.n490808114.train.util.TrainConstants;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TokenService.class)
public class TokenService {
    @MockBean UserDao userDao;
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

        JwtBuilder builder = Jwts.builder()
                .setId(""+user.getId())                          // JWT_ID
                .setAudience("")                                // 接受者
                .setClaims(claims)                                // 自定义属性
                .setSubject("")                                 // 主题
                .setIssuer("JAVA_HRM")                         // 签发者
                .setIssuedAt(start)                            // 签发时间
                .setNotBefore(start)                        // 失效时间
                .setExpiration(end)                      // 过期时间
                .signWith(algorithm, secretKey);                // 签名算法以及密匙
        return builder.compact();
    }
    private SecretKey getSecretKey(){
        //秘钥字段从常量类中获得
        String stringKey = TrainConstants.JWT_SECRET;
        byte[] encodeKey = Base64.decodeBase64(stringKey);
        return new SecretKeySpec(encodeKey,0,encodeKey.length,"AES");
    }

    public UserDetails getUserFromToken(String authToken) {
        if(authToken == null || authToken.equals("")) {return  null;}
        SecretKey secretKey = getSecretKey();
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken).getBody();
        return userDao.findById((Integer)claims.get("id"));
    }

    public void loginOut(String token) {

    }
    @Test
    public void testService() {

        User user = new User(1,
                "管理员",
                "490808114@qq.com",
                "admin",
                "0000000",
                "ROLE_USER#ROLE_ADMIN",
                new Date());
        Mockito.when(userDao.findById(1)).thenReturn(user);
        Mockito.when(userDao.findByUsernameAndPassword("admin","0000000")).thenReturn(user);
        Mockito.when(userDao.findByUsernameAndPassword("admin","00000")).thenReturn(null);

        System.out.println(userDao.findById(1).getId());
        String token1 = loginIn("admin","0000000");
        String token2 = loginIn("admin","00000");
        System.out.println("token1:"+token1);
        System.out.println("token2:"+token2);
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){ex.printStackTrace();}
        UserDetails user1 = getUserFromToken(token1);
        UserDetails user2 = getUserFromToken(token2);
        System.out.println("User1 : username:" + user1.getUsername() + " password:"+ user1.getPassword());
        System.out.println("User2 : username:" + (user2 == null?"null":user2.getUsername()) + " password:"+ (user2 == null?"null":user2.getPassword()));
    }
}
