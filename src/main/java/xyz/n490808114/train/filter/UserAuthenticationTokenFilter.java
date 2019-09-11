package xyz.n490808114.train.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.n490808114.train.domain.User;
import xyz.n490808114.train.service.TokenService;
import xyz.n490808114.train.util.TrainConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter("/**")
public class UserAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    private static Log log = LogFactory.getLog(UserAuthenticationTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authToken = null;
        //获取request头部中的Authorization字段
        String requestHeader =httpServletRequest.getHeader("Authorization");
        //获取token,token以"Bearer "开头
        if(requestHeader != null && requestHeader.startsWith("Bearer ")){
            authToken = requestHeader.substring(7);
        }
        if(authToken != null && authToken.split("\\.").length == 3){
            User user =null;
            //如果获取到token,通过token获取到对应的用户类
            user = tokenService.getUserFromToken(authToken);
            if(user != null){
                //把UserDetails放入SecurityContext Security上下文中，以供spring使用
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
                        null,
                        user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                httpServletRequest.setAttribute(TrainConstants.USER_REQUEST,user);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        //让filterChain继续处理
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

}
