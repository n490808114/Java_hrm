package xyz.n490808114.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@MapperScan("xyz.n490808114.test")
public class TrainApplicationTests {
	@Test
	public void contextLoads() {
	}

}
