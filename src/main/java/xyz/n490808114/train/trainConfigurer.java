package xyz.n490808114.train;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.n490808114.train.aop.LoginInterceptor;

/*@Configuration */
public class TrainConfigurer implements WebMvcConfigurer {
    /**
     * 如果使用MVC方式的话需要加Interceptor拦截器在这里
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/login/**","/js/**","/css/**");
    }
    /* 添加fastjson进入converters 
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();

        config.setSerializerFeatures(SerializerFeature.PrettyFormat);

        fastConverter.setFastJsonConfig(config);
        converters.add(fastConverter);
    }*/

}
