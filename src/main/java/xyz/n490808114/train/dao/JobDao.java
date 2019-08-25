package xyz.n490808114.train.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import xyz.n490808114.train.domain.Job;

import java.util.List;


public interface JobDao {
        @Select("SELECT * FROM job_inf")
        @Results(id = "jobResult",value = { @Result(id = true, column = "id", property = "id"),
                        @Result(column = "name", property = "name"), @Result(column = "remark", property = "remark"),
                        @Result(column = "id", property = "employees", many = @Many(select = "xyz.n490808114.train.dao.EmployeeDao.selectEmployeesByJobId", fetchType = FetchType.LAZY)) })
        List<Job> selectAll();

        @Select("SELECT * FROM job_inf WHERE id = #{id}")
        @ResultMap("jobResult")
        Job selectById(int id);

        @Select("SELECT * FROM job_inf WHERE name = #{name}")
        Job selectByName(String name);

        @Insert("INSERT INTO job_inf(id,name,remark) VALUES(#{id},#{name},#{remark})")
        void save(Job job);

        @Delete("DELETE FROM job_inf WHERE id = #{id}")
        void deleteById(int id);

        @Update("UPDATE job_int SET name = #{name},remark = #{remark} WHERE id = #{id}")
        void modify(Job job);
}
