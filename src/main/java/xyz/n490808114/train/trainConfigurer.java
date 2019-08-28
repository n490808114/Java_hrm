package xyz.n490808114.train;

import java.util.List;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.n490808114.train.aop.LoginInterceptor;

@Configuration
public class TrainConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/login/**","/js/**","/css/**");
    }
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();

        config.setSerializerFeatures(SerializerFeature.PrettyFormat);

        fastConverter.setFastJsonConfig(config);
        converters.add(fastConverter);
        for(HttpMessageConverter<?> converter :converters){
            System.out.println(converter);
        }
        
    }

}
