package xyz.n490808114.dao;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class EmployeeDynaSqlProvider {
    public String selectWithParam(Map<String,Object> param){
        return new SQL(){
            {
                SELECT("*");
                FROM("employee_inf");
                if(param.get("id") != null){
                    WHERE("id = #{id}");
                }
                if(param.get("name")!=null){
                    WHERE("name = #{name}");
                }
                if (param.get("cardId") != null){
                    WHERE("card_id = #{cardId}");
                }
                if (param.get("address") != null){
                    WHERE("address = #{address}");
                }
                if (param.get("postCode") != null){
                    WHERE("post_code = #{postCode} ");
                }
                if (param.get("tel") != null){
                    WHERE("tel = #{tel}");
                }
                if (param.get("phone") != null){
                    WHERE("phone = #{phone}");
                }
                if (param.get("qqNum") != null){
                    WHERE("qq_num = #{qqNum}");
                }
                if (param.get("email") != null){
                    WHERE("email = #{email}");
                }
                if (param.get("sex") != null){
                    WHERE("sex = #{sex}");
                }
                if (param.get("party") != null){
                    WHERE("party = #{party}");
                }
                if (param.get("birthday") != null){
                    WHERE("birthday = #{birthday}");
                }
                if (param.get("race") != null){
                    WHERE("race = #{race}");
                }
                if (param.get("education") != null){
                    WHERE("education = #{education}");
                }
                if (param.get("speciality") != null){
                    WHERE("speciality = #{speciality}");
                }
                if (param.get("hobby") != null){
                    WHERE("hobby = #{hobby}");
                }
                if (param.get("remark") != null){
                    WHERE("remark = #{remark}");
                }
                if (param.get("createDate") != null){
                    WHERE("create_date = #{createDate}");
                }
            }
        }.toString();
    }
}
