package xyz.n490808114.test.ControllerTest;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.n490808114.test.SqlTest.MapTest;
import xyz.n490808114.train.domain.Notice;

@RestController
public class NoticeController{
    private static Log log = LogFactory.getLog(NoticeController.class); 
    @Autowired
    MapTest mapTest;
    @Test
    @RequestMapping("test")
    public void test(){
    }
}