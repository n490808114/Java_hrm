package xyz.n490808114.train.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.Alias;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import xyz.n490808114.train.domain.User;

import java.util.List;
import java.util.Map;


public interface UserDao {

    @Select("select * from user_inf where loginname = #{loginName} and password = #{password}")
    User selectByLoginNameAndPassword(@Param("loginName") String loginName, @Param("password") String password);


    @Select("select * from user_inf where id = #{id}")
    @Results(id = "userResult",value = { @Result(id = true, column = "id", property = "id"),
            @Result(column = "loginname", property = "loginName"), @Result(column = "password", property = "password"),
            @Result(column = "status", property = "status"), @Result(column = "createdate", property = "createDate"),
            @Result(column = "username", property = "userName"), @Result(column = "email", property = "email") })
    User selectById(int id);


    @Delete("delete from user_inf where id = #{id}")
    void deleteById(int id);


    @SelectProvider(type = UserDynaSqlProvider.class, method = "updateUser")
    void update(User user);


    @SelectProvider(type = UserDynaSqlProvider.class, method = "selectWithParam")
    List<User> selectByPage(Map<String, Object> params);


    @SelectProvider(type = UserDynaSqlProvider.class, method = "count")
    int count(Map<String, Object> params);


    @SelectProvider(type = UserDynaSqlProvider.class, method = "insertUser")
    void save(User user);
}
