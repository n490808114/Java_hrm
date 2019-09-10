package xyz.n490808114.train.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import xyz.n490808114.train.domain.Document;

import java.util.List;
import java.util.Map;

@Repository
public interface DocumentDao {

        @Select("SELECT * FROM document_inf WHERE id = #{id}")
        @Results(id = "documentResult",value = { @Result(id = true, column = "id", property = "id"), 
        @Result(column = "title", property = "title"),
                        @Result(column = "filename", property = "fileName"),
                        @Result(column = "remark", property = "remark"),
                        @Result(column = "create_date", property = "createDate"),
                        @Result(column = "user_id", property = "user", one = @One(select = "xyz.n490808114.train.dao.UserDao.findById", fetchType = FetchType.EAGER)) })
        Document selectById(int id);

        @Delete("DELETE FROM document_inf WHERE id = #{id}")
        void deleteById(int id);

        @Insert("INSERT INTO document_inf(title,filename,remark,create_date,user_id) "
                        + "VALUES(#{title},#{fileName},#{remark},#{createDate},#{user.id})")
        @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
        void save(Document document);

        @Update("UPDATE document_inf SET title = #{title}," + "filename = #{fileName}," + "remark = #{remark},"
                        + "create_date = #{createDate}," + "user_id = #{user.id}")
        void modify(Document document);

        @SelectProvider(type = DocumentDynaSqlProvider.class,method = "getListByParam")
        @Results(id = "documentListResult",value = {
                @Result(id = true, column = "id", property = "id"),
                @Result(column = "title", property = "title"),
                @Result(column = "create_date", property = "createDate"),
                @Result(column = "user_id", property = "user", 
                        one = @One(select = "xyz.n490808114.train.dao.UserDao.findById", 
                                fetchType = FetchType.EAGER)) })
        @ResultType(Document.class)
        @MapKey("id")
        Map<Integer, Document> getList(Map<String, String> param);

        @SelectProvider(type = DocumentDynaSqlProvider.class,method = "getCountByParam")
        int getCount(Map<String, String> param);
}
