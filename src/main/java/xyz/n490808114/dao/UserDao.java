package xyz.n490808114.dao;

import org.apache.ibatis.annotations.*;
import xyz.n490808114.domain.User;

import java.util.List;
import java.util.Map;

import static xyz.n490808114.util.HrmConstants.USER_TABLE;

public interface UserDao {
    // ���ݵ�¼���������ѯԱ��
    @Select("select * from " + USER_TABLE + " where loginname = #{loginName} and password = #{password}")
    User selectByLoginNameAndPassword(@Param("loginName") String loginName, @Param("password") String password);

    // ����id��ѯԱ��
    @Select("select * from " + USER_TABLE + " where id = #{id}")
    @Results(id = "userResult",value = { @Result(id = true, column = "id", property = "id"),
            @Result(column = "loginname", property = "loginName"), @Result(column = "password", property = "password"),
            @Result(column = "status", property = "status"), @Result(column = "createdate", property = "createDate"),
            @Result(column = "username", property = "userName"), @Result(column = "email", property = "email") })
    User selectById(int id);

    // ����idɾ��Ա��
    @Delete("delete from " + USER_TABLE + " where id = #{id}")
    void deleteById(int id);

    // ��̬�޸��û�
    @SelectProvider(type = UserDynaSqlProvider.class, method = "updateUser")
    void update(User user);

    // ��̬��ѯ
    @SelectProvider(type = UserDynaSqlProvider.class, method = "selectWithParam")
    List<User> selectByPage(Map<String, Object> params);

    // ���ݲ�����ѯ�û�����
    @SelectProvider(type = UserDynaSqlProvider.class, method = "count")
    int count(Map<String, Object> params);

    // ��̬�����û�
    @SelectProvider(type = UserDynaSqlProvider.class, method = "insertUser")
    void save(User user);
}
