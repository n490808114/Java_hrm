package xyz.n490808114.dao;

import org.apache.ibatis.jdbc.SQL;
import xyz.n490808114.domain.User;

import java.util.Map;

import static xyz.n490808114.util.HrmConstants.USER_TABLE;

public class UserDynaSqlProvider {

    //分页动态查询
    public String selectWithParam(Map<String,Object> params){
        String sql = new SQL(){
            {
                SELECT("*");
                FROM(USER_TABLE);
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
    //动态查询总数量
    public String count(Map<String,Object> params){
        return new SQL(){
            {
                SELECT("count(*)");
                FROM(USER_TABLE);
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
    //动态插入
    public String insertUser(User user){
        return new SQL(){
            {
                INSERT_INTO(USER_TABLE);
                if(user.getUserName() != null && !user.getUserName().equals("")){
                    VALUES("username","#{userName}");
                }
                if(user.getStatus() != 0){
                    VALUES("status","#{status}");
                }
                if(user.getLoginName() != null && !user.getLoginName().equals("")){
                    VALUES("loginname","#{loginName}");
                }
                if(user.getPassword() != null && !user.getPassword().equals("")){
                    VALUES("password","#{password}");
                }
            }
        }.toString();
    }
    //动态更新
    public String updateUser(User user){
        return new SQL(){
            {
                UPDATE(USER_TABLE);
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
