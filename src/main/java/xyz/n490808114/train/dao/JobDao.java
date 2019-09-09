package xyz.n490808114.train.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import xyz.n490808114.train.domain.Job;

import java.util.List;
import java.util.Map;

@Repository
public interface JobDao {
        @Select("SELECT id,name,remark FROM job_inf")
        @Results(id = "jobListResult",value = { @Result(id = true, column = "id", property = "id"),
                        @Result(column = "name", property = "name"), 
                        @Result(column = "remark", property = "remark")})
        List<Job> selectAll();

        @Select("SELECT * FROM job_inf WHERE id = #{id}")
        @Results(id = "jobResult",value = { @Result(id = true, column = "id", property = "id"),
                                        @Result(column = "name", property = "name"), 
                                        @Result(column = "remark", property = "remark"),
                                        @Result(column = "id", property = "employees", many = @Many(select = "xyz.n490808114.train.dao.EmployeeDao.selectEmployeesByJobId", fetchType = FetchType.LAZY)) })
        Job selectById(int id);

        @SelectProvider(type = JobDynaSqlProvider.class,method = "getListByParam")
        @ResultType(Job.class)
        @MapKey("id")
        Map<Integer,Job> getList(Map<String,String> param);

        @SelectProvider(type = JobDynaSqlProvider.class,method = "getCountByParam")
        int getCountByParam(Map<String,String> param);
        

        @Select("SELECT * FROM job_inf WHERE name = #{name}")
        Job selectByName(String name);

        @Insert("INSERT INTO job_inf(id,name,remark) VALUES(#{id},#{name},#{remark})")
        void save(Job job);

        @Delete("DELETE FROM job_inf WHERE id = #{id}")
        void deleteById(int id);

        @Update("UPDATE job_inf SET name = #{name},remark = #{remark} WHERE id = #{id}")
        void modify(Job job);
}
