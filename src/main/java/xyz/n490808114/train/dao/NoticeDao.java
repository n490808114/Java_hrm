package xyz.n490808114.train.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Service;
import xyz.n490808114.train.domain.Notice;

import java.util.*;


public interface NoticeDao {

        @Select("SELECT * FROM notice_inf WHERE id = #{id}")
        @Results(id = "noticeResult",value = { @Result(id = true, column = "id", property = "id"), 
                                                @Result(column = "title", property = "title"),
                                                @Result(column = "content", property = "content"),
                                                @Result(column = "create_date", property = "createDate"),
                                                @Result(column = "user_id", property = "user", 
                                                one = @One(select = "xyz.n490808114.train.dao.UserDao.selectById", fetchType = FetchType.EAGER)) })
        Notice selectById(int id);

        @Select("SELECT * FROM notice_inf")
        @ResultMap("noticeResult")
        List<Notice> selectAll();

        @SelectProvider(type = NoticeDynaSqlProvider.class,method = "getNoticeListByPageNoAndPageSize")
        @Results(id = "noticeList",value = { @Result(id = true, column = "id", property = "id"), 
                                                @Result(column = "title", property = "title"),
                                                @Result(column = "create_date", property = "createDate"),
                                                @Result(column = "user_id", property = "user", 
                                                one = @One(select = "xyz.n490808114.train.dao.UserDao.selectById", fetchType = FetchType.EAGER)) })
        List<Notice> getNoticeList(Map<String,Object> param);

        @Delete("DELETE FROM notice_inf WHERE id = #{id}")
        void deleteById(int id);

        @Insert("INSERT INTO notice_inf(title,content,create_date,user_id) "
                        + "VALUES(#{title},#{content},#{createDate},#{user.id})")
        void save(Notice notice);

        @Update("UPDATE notice_inf SET title = #{title}," + "content = #{content}," + "create_date = #{createDate},"
                        + "user_id = #{user.id} " + "WHERE id = #{id}")
        void modify(Notice notice);

        @Select("SELECT COUNT(id) from notice_inf")
        int getCount();

}
