package xyz.n490808114.train.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.n490808114.train.domain.User;

@Repository
public interface UserDao {

    @Select("SELECT * FROM user_inf WHERE username = #{username}")
    User findByUsername(String username);
    @Select("SELECT * FROM user_inf WHERE ID = #{id}")
    User findById(int id);

    @Select("Select * FROM user_inf WHERE username =#{username} AND password = #{password}")
    User findByUsernameAndPassword(String username,String password);
}
