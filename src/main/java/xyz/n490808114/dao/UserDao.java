package xyz.n490808114.dao;

import org.apache.ibatis.annotations.*;
import xyz.n490808114.domain.User;

import java.util.List;
import java.util.Map;

import static xyz.n490808114.util.HrmConstants.USER_TABLE;

public interface UserDao {
    //根据登录名和密码查询员工
    @Select("select * from " + USER_TABLE + " where loginname = #{loginName} and password = #{password}")
    User selectByLoginNameAndPassword(@Param("loginName") String loginName, @Param("password") String password);

    //根据id查询员工
    @Select("select * from " + USER_TABLE + " where id = #{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "loginname",property = "loginName"),
            @Result(column = "password",property = "password"),
            @Result(column = "status",property = "status"),
            @Result(column = "createdate",property = "createDate"),
            @Result(column = "username",property = "userName"),
            @Result(column = "email",property = "email")
    })
    User selectById(int id);

    //根据id删除员工
    @Delete("delete from " + USER_TABLE + " where id = #{id}")
    void deleteById(int id);

    //动态修改用户
    @SelectProvider(type = UserDynaSqlProvider.class,method = "updateUser")
    void update(User user);

    //动态查询
    @SelectProvider(type = UserDynaSqlProvider.class,method = "selectWithParam")
    List<User> selectByPage(Map<String,Object> params);

    //根据参数查询用户总数
    @SelectProvider(type = UserDynaSqlProvider.class,method = "count")
    int count(Map<String,Object> params);

    //动态插入用户
    @SelectProvider(type = UserDynaSqlProvider.class,method = "insertUser")
    void save(User user);
}

