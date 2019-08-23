package xyz.n490808114.train;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication()
@MapperScan("xyz.n490808114.train.dao")
public class TrainApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrainApplication.class,args);
    }
}
