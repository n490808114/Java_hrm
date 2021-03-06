package xyz.n490808114.train;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
@MapperScan("xyz.n490808114.train.dao")
public class TrainApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrainApplication.class,args);
    }
    /*
        public HttpMessageConverters fastJsonHttpMessageConverters(){
            FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
            FastJsonConfig config = new FastJsonConfig();

            config.setSerializerFeatures(SerializerFeature.PrettyFormat);

            fastConverter.setFastJsonConfig(config);
            HttpMessageConverter<?> converter = fastConverter;
            return new HttpMessageConverters(converter);
        }
    */
    
}
