package xyz.n490808114.test.ControllerTest;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.n490808114.test.SqlTest.MapTest;

@RestController
public class NoticeController{
    private static Log log = LogFactory.getLog(NoticeController.class); 
    @Autowired
    MapTest mapTest;
    @Test
    @RequestMapping("test")
    public void test(){
        log.info("message");
    }
}