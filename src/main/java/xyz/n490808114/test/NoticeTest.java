package xyz.n490808114.test;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import xyz.n490808114.dao.SSFactory;
import xyz.n490808114.dao.UserDao;
import xyz.n490808114.domain.Notice;
import xyz.n490808114.domain.User;
import xyz.n490808114.service.HrmService;

import java.text.DateFormat;
import java.util.Date;

public class NoticeTest {
    @Autowired
    @Qualifier("HrmServerImpl")
    private HrmService service;
    public static void main(String[] args){
        new NoticeTest().insertNotice();
    }
    public void insertNotice(){
        SqlSession sqlSession = SSFactory.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User admin1 = userDao.selectById(1);
        User admin = service.findUserById(1);

    }
    public static void testDate(){
        Date test= new Date();

    }
}
