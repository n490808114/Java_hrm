package xyz.n490808114.train.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xyz.n490808114.train.domain.Dept;

import java.util.*;

public interface DeptDao {

        @Select("SELECT * FROM dept_inf")
        @Results(id = "deptResult",value = { @Result(id = true, column = "id", property = "id"), @Result(column = "name", property = "name"),
                        @Result(column = "remark", property = "remark"),
                        @Result(column = "id", property = "employees", many = @Many(select = "xyz.n490808114.dao.EmployeeDao.selectEmployeesByDeptId", fetchType = FetchType.LAZY)) })
        List<Dept> selectAll();

        @Select("SELECT COUNT(id) FROM dept_inf")
        int getCount();

        @Select("SELECT * FROM dept_inf WHERE id = #{id}")
        @ResultMap("deptResult")
        Dept selectById(int id);

        @SelectProvider(type = DeptDynaSqlProvider.class,method = "getDeptListByPageNoAndPageSize")
        @ResultMap("deptResult")
        List<Dept> getDeptList(String pageNo,String pageSize);

        @Select("SELECT * FROM dept_inf WHERE name =#{name}")
        Dept selectByName(String name);

        @Delete("DELETE FROM dept_inf WHERE id = #{id}")
        void deleteById(int id);

        @Insert("INSERT INTO dept_inf(id,name,remark) VALUES(#{id},#{name},#{remark}) ")
        void save(Dept dept);

        @Update("UPDATE dept_inf SET name = #{name},remark = #{remark} WHERE id = #{id}")
        void modify(Dept dept);

}