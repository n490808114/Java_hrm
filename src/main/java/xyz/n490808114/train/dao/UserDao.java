package xyz.n490808114.train.dao;

import org.apache.ibatis.annotations.Select;
import xyz.n490808114.train.domain.User;

public interface UserDao {

    @Select("SELECT * FROM user_inf WHERE username = #{username}")
    User findByUsername(String username);
    @Select("SELECT * FROM user_inf WHERE ID = #{id}")
    User findById(int id);
}
