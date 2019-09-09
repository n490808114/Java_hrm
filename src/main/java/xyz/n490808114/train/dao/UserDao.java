package xyz.n490808114.train.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
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

    @Insert("INSERT INTO user_inf (username,email,password) VALUES(#{username},#{email},#{password})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int insert(User user);
}
