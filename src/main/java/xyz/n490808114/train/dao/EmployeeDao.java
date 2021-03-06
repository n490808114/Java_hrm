package xyz.n490808114.train.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import xyz.n490808114.train.domain.Employee;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeDao {
        @InsertProvider(type = EmployeeDynaSqlProvider.class,method = "insert")
        @Options(useGeneratedKeys = true, keyProperty = "id")
        int save(Employee employee);

        @InsertProvider(type = EmployeeDynaSqlProvider.class,method = "insertByParam")
        int insert(Map<String,String> param);

        @Delete("DELETE FROM employee_inf WHERE id =#{id}")
        int deleteById(@Param("id") int id);

        @UpdateProvider(type = EmployeeDynaSqlProvider.class,method = "modifyByEmployee")
        void modify(Employee employee);

        @UpdateProvider(type = EmployeeDynaSqlProvider.class,method = "modifyByParam")
        void modifyByParam(Map<String,String> param);

        @Select("SELECT * FROM employee_inf WHERE id = #{id}")
        @Results(id = "employeeResult",value = {
                        @Result(id = true, column = "id", property = "id"), 
                        @Result(column = "name", property = "name"),
                        @Result(column = "card_id", property = "cardId"),
                        @Result(column = "address", property = "address"),
                        @Result(column = "post_code", property = "postCode"), 
                        @Result(column = "tel", property = "tel"),
                        @Result(column = "phone", property = "phone"), 
                        @Result(column = "qq_num", property = "qqNum"),
                        @Result(column = "email", property = "email"), 
                        @Result(column = "sex", property = "sex"),
                        @Result(column = "party", property = "party"),
                        @Result(column = "birthday", property = "birthday"),
                        @Result(column = "race", property = "race"),
                        @Result(column = "education", property = "education"),
                        @Result(column = "speciality", property = "speciality"),
                        @Result(column = "hobby", property = "hobby"), 
                        @Result(column = "remark", property = "remark"),
                        @Result(column = "create_date", property = "createDate"),
                        @Result(column = "dept_id", property = "dept", one = @One(select = "xyz.n490808114.train.dao.DeptDao.selectById", fetchType = FetchType.EAGER)),
                        @Result(column = "job_id", property = "job", one = @One(select = "xyz.n490808114.train.dao.JobDao.selectById", fetchType = FetchType.EAGER)) })
        Employee selectById(int id);

        @Select("SELECT * FROM employee_inf")
        List<Employee> selectAll();
        
        @Select("SELECT * FROM employee_inf WHERE dept_id = #{id}")
        @ResultMap("employeeResult")
        List<Employee> selectEmployeesByDeptId(int id);

        @Select("SELECT * FROM employee_inf WHERE job_id = #{id}")
        @ResultMap("employeeResult")
        List<Employee> selectEmployeesByJobId(int id);

        @Update("Update employee_inf set job_id = #{after} WHERE job_id = #{before}")
        void changeAllThisJob(int before,int after);
        

        @SelectProvider(type = EmployeeDynaSqlProvider.class, method = "selectWithParam")
        @ResultMap("employeeResult")
        List<Employee> selectEmployeesWithParam(Map<String, Object> param);

        @SelectProvider(type = EmployeeDynaSqlProvider.class,method = "getEmployeeListByParam")
        @Results(id="employeeList",value = {
                @Result(id=true,column = "id",property = "id"),
                @Result(column = "name", property = "name"),
                @Result(column = "dept_id", property = "dept", one = @One(select = "xyz.n490808114.train.dao.DeptDao.selectById", fetchType = FetchType.EAGER)),
                @Result(column = "job_id", property = "job", one = @One(select = "xyz.n490808114.train.dao.JobDao.selectById", fetchType = FetchType.EAGER)),
                @Result(column = "phone", property = "phone")
        })
        @ResultType(Employee.class)
        @MapKey("id")
        Map<Integer,Employee> getList(Map<String,String> param);

        @SelectProvider(type = EmployeeDynaSqlProvider.class,method = "getCount")
        int getCount(Map<String, String> param);

        @Select("SELECT COUNT(id) FROM employee_inf WHERE dept_id = #{deptId}")
        int getCountByDeptId(int deptId);

        @Select("SELECT COUNT(id) FROM employee_inf WHERE job_id = #{jobId}")
        int getCountByJobId(int jobId);

}
