package xyz.n490808114.train.dao;

import org.apache.ibatis.jdbc.SQL;
import xyz.n490808114.train.domain.User;

import java.util.Map;


public class UserDynaSqlProvider {


    public String selectWithParam(Map<String,Object> params){
        String sql = new SQL(){
            {
                SELECT("*");
                FROM("user_inf");
                if(params.get("user") != null){
                    User user = (User) params.get("user");
                    if(user.getUserName() != null && !user.getUserName().equals("")){
                        WHERE(" username LIKE CONCAT ('%',#{user.userName},'%')");
                    }
                    if(user.getStatus() != 0){
                        WHERE(" status LIKE CONCAT ('%',#{user.status},'%')");
                    }
                }
            }
        }.toString();
        if(params.get("pageModel") != null){
            sql += "limit #{pageModel.firstLimitParam},#{pageModel.pageSize}";
        }
        return sql;
    }

    public String count(Map<String,Object> params){
        return new SQL(){
            {
                SELECT("count(*)");
                FROM("user_inf");
                if(params.get("user") !=null){
                    User user = (User) params.get("user");
                    if(user.getUserName() != null && !user.getUserName().equals("")){
                        WHERE(" username LIKE CONCAT ('%',#{user,userName},'%')");
                    }
                    if(user.getStatus() != 0){
                        WHERE(" status LIKE CONCAT ('%',#{user.status},'%')");
                    }
                }
            }
        }.toString();
    }

    public String insertUser(User user){
        return new SQL(){
            {
                INSERT_INTO("user_inf");
                if(user.getUserName() != null && !user.getUserName().equals("")){
                    VALUES("username","#{userName}");
                }
                if(user.getStatus() != null){
                    VALUES("status","#{status}");
                }
                if(user.getLoginName() != null && !user.getLoginName().equals("")){
                    VALUES("loginname","#{loginName}");
                }
                if(user.getPassword() != null && !user.getPassword().equals("")){
                    VALUES("password","#{password}");
                }
                if(user.getEmail() != null && !user.getEmail().equals("")){
                    VALUES("email","#{email}");
                }
            }
        }.toString();
    }

    public String updateUser(User user){
        return new SQL(){
            {
                UPDATE("user_inf");
                if(user.getUserName() != null){
                    SET(" username = #{username} ");
                }
                if(user.getLoginName() != null){
                    SET("loginname = #{loginName}");
                }
                if(user.getPassword() != null){
                    SET(" password = #{password} ");
                }
                if(user.getStatus() != 0){
                    SET(" status = #{status} ");
                }
                if(user.getCreateDate() != null){
                    SET(" create_date = #{createDate}");
                }
                WHERE(" id = #{id} ");
            }
        }.toString();
    }
}
