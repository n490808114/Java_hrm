package xyz.n490808114.train;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import xyz.n490808114.train.service.HrmService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,jsr250Enabled = false)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("hrmServiceImpl")
    private HrmService hrmService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* REST式的配置方法：关闭spring Security的全局验证登录，设置所有请求不做限制 */
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests().anyRequest().permitAll();
        /*  MVC的配置方法：
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .antMatchers("/manager").hasRole("admin")
            .antMatchers("/register","/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login.html")
            .and()
            .logout()
            .logoutSuccessUrl("/login.html");*/
    }
    /*
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login.html","/register.html","/favicon.ico","/js/**","/css/**");
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
        UserDetailsService service = (String username) ->{
            UserDetails userDetails = null;
            if(username.indexOf("@") >= 0){
                userDetails = hrmService.getUserByEmail(username);
            }else{
                userDetails = hrmService.getUserByName(username);
            }
            if(userDetails == null){throw new UsernameNotFoundException("用户名或密码错误，请重新登录！");}
            return userDetails;
        };


    }*/
}