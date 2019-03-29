package xyz.n490808114.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import xyz.n490808114.domain.Document;

import java.util.List;

public interface DocumentDao {

    @Select("SELECT * FROM document_inf")
    @Results({
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "title",property = "title"),
            @Result(column = "filename",property = "fileName"),
            @Result(column = "remark",property = "remark"),
            @Result(column = "create_date",property = "createDate"),
            @Result(column = "id" ,property = "user",
                    one = @One(select = "xyz.n490808114.dao.UserDao.selectById",
                                fetchType = FetchType.EAGER))
    })
    List<Document> selectAll();

    @Select("SELECT * FROM document_inf WHERE id = #{id}")
    @Results({
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "title",property = "title"),
            @Result(column = "filename",property = "fileName"),
            @Result(column = "remark",property = "remark"),
            @Result(column = "create_date",property = "createDate"),
            @Result(column = "id" ,property = "user",
                    one = @One(select = "xyz.n490808114.dao.UserDao.selectById",
                            fetchType = FetchType.EAGER))
    })
    Document selectById(int id);

    @Delete("DELETE * FROM document_inf WHERE id = #{id}")
    void deleteById(int id);

    @Insert("INSERT INTO document_inf(id,title,filename,remark,create_date,user_id) " +
                                "VALUES(#{id},#{title},#{fileName},#{remark},#{createDate},#{user.id})")
    void save(Document document);

    @Update("UPDATE document_inf SET title = #{title}," +
                                    "filename = #{fileName}," +
                                    "remark = #{remark}," +
                                    "create_date = #{createDate}," +
                                    "user_id = #{user.id}")
    void modify(Document document);
}
