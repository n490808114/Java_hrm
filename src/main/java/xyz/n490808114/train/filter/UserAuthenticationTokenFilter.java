package xyz.n490808114.train.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.n490808114.train.dao.UserDao;
import xyz.n490808114.train.util.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDao userDao;
    private static Log log = LogFactory.getLog(UserAuthenticationTokenFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authentication");
        if(authHeader != null && authHeader.startsWith("bearer ")){
            String authToken = authHeader.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            log.info("checking authentication :"+username);
            if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDao.findByUsername(username);
                if(jwtTokenUtil.validateToken(authToken,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken  = new UsernamePasswordAuthenticationToken(
                            userDetails,null,userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authenticated user " +username +",setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
    }
}
