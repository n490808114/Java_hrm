package xyz.n490808114.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/notice")
public class NoticeControllerTest {

    @GetMapping
    public Map<String,Object> get(){
        Map<String,Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019,Calendar.AUGUST,07,14,12,30);
        map.put("test",calendar.getTime());
        return map;
    }
}
