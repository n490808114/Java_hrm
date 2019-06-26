package xyz.n490808114.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SSFactory {
    static SqlSessionFactory factory;
    static {
        try{
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            factory = new SqlSessionFactoryBuilder().build(inputStream);
        }catch (IOException ex){ex.printStackTrace();}
    }
    public static SqlSession getSqlSession(){
        return factory.openSession();
    }
}
