package xyz.n490808114.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.n490808114.domain.User;
import xyz.n490808114.util.HrmConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class AuthorizedInterceptor implements HandlerInterceptor {
    /** 定义不需要拦截的请求 */
    private static final String[] IGNORE_URL ={"login","/404.html","/register",
                                                "/js/*.js","/css/*.css","/picture/*.jpg"};

    /**
     * preHandle方法是进行处理器拦截用的，该方法将在Controller处理之前进行调用,
     * 当preHandle的返回值为false的时候整个请求就结束了.
     * 如果preHandle的返回值为true,则会继续执行postHandle和afterCompletion.
     * @see {HandlerInterceptor}
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        boolean flag =false;
        String servletPath = request.getServletPath();
        for(String s:IGNORE_URL){
            if(servletPath.contains(s)){
                flag = true;
                break;
            }
        }
        if(!flag){
            User user = (User) request.getSession().getAttribute(HrmConstants.USER_SESSION);
            if(user == null){
                response.sendRedirect(request.getContextPath() + "/login");
                return flag;
            }else{
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 该方法需要preHandle方法的返回值为true时才会执行.
     * 执行时间是在处理器进行处理之后，也就是在Controller的方法调用之后执行.
     * @see {HandlerInterceptor}
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView)
            throws Exception {

    }
    /**
     * 该方法需要preHandle方法的返回值为true时才会执行.
     * 该方法将在整个请求完成之后执行,主要用于清理资源.
     * @see {HandlerInterceptor}
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex)
            throws Exception {

    }

}
