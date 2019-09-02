package xyz.n490808114.train.util;

import org.springframework.security.core.userdetails.UserDetails;
import xyz.n490808114.train.domain.User;

public class JwtTokenUtil {
    public String getUsernameFromToken(String token){
        return "";
    }
    public String getTokenFromUser(User user){
        return "";
    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        return true;
    }
}
