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
    /** ���岻��Ҫ���ص����� */
    private static final String[] IGNORE_URL ={"login","/404.html","/register",
                                                "/js/*.js","/css/*.css","/picture/*.jpg"};

    /**
     * preHandle�����ǽ��д����������õģ��÷�������Controller����֮ǰ���е���,
     * ��preHandle�ķ���ֵΪfalse��ʱ����������ͽ�����.
     * ���preHandle�ķ���ֵΪtrue,������ִ��postHandle��afterCompletion.
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
     * �÷�����ҪpreHandle�����ķ���ֵΪtrueʱ�Ż�ִ��.
     * ִ��ʱ�����ڴ��������д���֮��Ҳ������Controller�ķ�������֮��ִ��.
     * @see {HandlerInterceptor}
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView)
            throws Exception {

    }
    /**
     * �÷�����ҪpreHandle�����ķ���ֵΪtrueʱ�Ż�ִ��.
     * �÷������������������֮��ִ��,��Ҫ����������Դ.
     * @see {HandlerInterceptor}
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex)
            throws Exception {

    }

}
