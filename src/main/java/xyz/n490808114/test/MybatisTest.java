package xyz.n490808114.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.PropertyConfigurator;
import xyz.n490808114.dao.UserDao;
import xyz.n490808114.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.logging.Logger;

public class MybatisTest {
    static Logger logger = Logger.getLogger(MybatisTest.class.getName());
    public static void main(String[] args){
        String path = MybatisTest.class.getResource("/").getPath();
        System.out.println(path+ "properties/log4j.properties");
        PropertyConfigurator.configure(path+ "properties/log4j.properties");
        logger.info("test");
        try{
            new MybatisTest().requestMapTest();
        }catch (IOException ex){ex.printStackTrace();}

    }
    public SqlSession getSession()throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession();
    }
    public void insert() throws IOException{
        User user = new User(7,"zhao","n490808114","8801668",1,new Date(),"490808114@qq.com");
        SqlSession sqlSession = getSession();
        sqlSession.insert("saveUser",user);
        sqlSession.commit();
        sqlSession.close();
    }
    public void select(int a)throws IOException{
        SqlSession sqlSession  = getSession();
        User user = sqlSession.selectOne("selectUser",a);
        logger.warning(user.toString());
        System.out.println(user.toString());
        sqlSession.commit();
        sqlSession.close();
    }
    public void requestMapTest()throws IOException{
        SqlSession sqlSession = getSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.selectById(1);

        System.out.println(user);

        sqlSession.commit();
        sqlSession.close();
    }
}
