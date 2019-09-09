package xyz.n490808114.train.service;

import org.springframework.stereotype.Service;
import xyz.n490808114.train.domain.*;
import xyz.n490808114.train.dto.ListDto;

import java.util.*;

@Service
public interface HrmService {

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    Employee findEmployeeById(int id);

    /**
     * 根据id删除员工
     * @param id
     */
    void removeEmployeeById(int id);

    /**
     * 添加员工
     * @param employee
     */
    void addEmployee(Employee employee);

    void addEmployee(Map<String,String> param);

    /**
     * 获得员工列表
     * @return employeeList
     */
    ListDto<Employee> getEmployeeList(Map<String,String> param);

    /**
     * 获得Employee总数
     * @return 总数
     */
    int getEmployeeCount(Map<String, String> param);

    int countEmployeesByDeptId(int deptId);

    int countEmployeesByJobId(int jobId);


    /**
     * 修改员工
     * @param employee
     */
    void modifyEmployee(Employee employee);

    /**
     * 根据Map修改员工,确定包含id
     * @param param
     */
    void modifyEmployee(Map<String,String> param);
    /**
     * 获取所有部门
     * @return Dept List
     */
    List<Dept> findAllDept();

    ListDto<Dept> getDeptList(Map<String,String> param);

    int getDeptCount();

    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    Dept findDeptById(int id);

    /**
     * 根据名字查询部门
     * @param name
     * @return
     */
    Dept findDeptByName(String name);
    /**
     * 根据id删除部门
     * @param id
     */
    void removeDeptById(int id);

    /**
     * 添加部门
     * @param dept
     */
    void addDept(Dept dept);

    /**
     * 修改部门
     * @param dept
     */
    void modifyDept(Dept dept);

    /**
     * 获取所有职位
     * @return Job List
     */
    List<Job> findAllJob();

    ListDto<Job> getJobList(Map<String,String> param);

    /**
     * 根据id查询职位
     * @param id
     * @return
     */
    Job findJobById(int id);

    /**
     * 根据name查询职位
     * @param name
     * @return
     */
    Job findJobByName(String name);
    /**
     * 添加职位
     * @param job
     */
    void addJob(Job job);

    /**
     * 根据id删除职位
     * @param id
     */
    void removeJobById(int id);

    /**
     * 修改职位
     * @param job
     */
    void modifyJob(Job job);

    /**
     * 获取所有文档
     * @return Document List
     */
    List<Document> findAllDocument();

    /**
     * 根据id查询文档
     * @param id
     * @return Document 对象
     */
    Document findDocumentById(int id);

    /**
     * 根据id删除文档
     * @param id
     */
    void removeDocument(int id);

    /**
     * 添加文档
     * @param document
     */
    void addDocument(Document document);

    /**
     * 修改文档
     * @param document
     */
    void modifyDocument(Document document);




    /**
     * 需要提供key 为 pageNo 和 pageSize 的数据
     */
    ListDto<Notice> getNoticeList(Map<String,String> param);

    /**
     * 根据id查询公告
     * @param id
     * @return Notice对象
     */
    Notice findNoticeById(int id);

    /**
     * 根据id删除公告
     * @param id
     */
    void removeNotice(int id);

    /**
     * 添加公告
     * @param notice
     */
    void addNotice(Notice notice);

    /**
     * 修改公告
     * @param notice
     */
    void modifyNotice(Notice notice);


}