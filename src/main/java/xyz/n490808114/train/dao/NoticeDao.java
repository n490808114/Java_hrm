package xyz.n490808114.train.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import xyz.n490808114.train.domain.Notice;

import java.util.*;

@Repository
public interface NoticeDao {

        @Select("SELECT * FROM notice_inf WHERE id = #{id}")
        @Results(id = "noticeResult",value = { @Result(id = true, column = "id", property = "id"), 
                                                @Result(column = "title", property = "title"),
                                                @Result(column = "content", property = "content"),
                                                @Result(column = "create_date", property = "createDate"),
                                                @Result(column = "user_id", property = "user", 
                                                one = @One(select = "xyz.n490808114.train.dao.UserDao.findById", fetchType = FetchType.EAGER)) })
        Notice selectById(int id);

        @Select("SELECT * FROM notice_inf")
        @ResultMap("noticeResult")
        List<Notice> selectAll();


        @SelectProvider(type = NoticeDynaSqlProvider.class,method = "getNoticeListByParam")
        @ResultType(Notice.class)
        @MapKey("id")
        @Results(id = "noticeList",value = { @Result(id = true, column = "id", property = "id"), 
                                                @Result(column = "title", property = "title"),
                                                @Result(column = "create_date", property = "createDate"),
                                                @Result(column = "user_id", property = "user",
                        one = @One(select = "xyz.n490808114.train.dao.UserDao.findById", fetchType = FetchType.EAGER))})
        Map<Integer,Notice> getNoticeList(Map<String,String> param);

        @Delete("DELETE FROM notice_inf WHERE id = #{id}")
        void deleteById(int id);

        @Insert("INSERT INTO notice_inf(title,content,create_date,user_id) "
                        + "VALUES(#{title},#{content},#{createDate},#{user.id})")
        @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
        void save(Notice notice);

        @Update("UPDATE notice_inf SET title = #{title},content = #{content} WHERE id = #{id}")
        void modify(Notice notice);

        @SelectProvider(type = NoticeDynaSqlProvider.class,method = "getCount")
        int getCount(Map<String, String> param);

}
