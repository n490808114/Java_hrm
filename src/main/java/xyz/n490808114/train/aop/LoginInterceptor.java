package xyz.n490808114.train.aop;

import org.springframework.web.servlet.HandlerInterceptor;
import xyz.n490808114.train.util.TrainConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String path = request.getServletPath();
        if( request.getSession().getAttribute(TrainConstants.USER_SESSION) == null
                && !path.equals("/login.html")
                && !path.equals("/register.html")
            ){
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
