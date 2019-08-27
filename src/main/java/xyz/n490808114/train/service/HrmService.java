package xyz.n490808114.train.service;

import xyz.n490808114.train.domain.*;

import java.util.*;

public interface HrmService {
    /**
     * 用户登录
     * @param loginName
     * @param password
     * @return User对象
     */
    User login(String loginName,String password);

    User loginByEmail(String email,String password);

    /**
     * 根据id查询用户
     * @param id
     * @return User对象
     */
    User findUserById(int id);

    int registerCheckName(String name);
    int registerCheckEmail(String email);

    /**
     * 根据id删除用户
     * @param id
     */
    void removeUserById(int id);

    /**
     * 修改用户
     * @param user
     */
    void modifyUser(User user);

    /**
     * 添加用户
     * @param user
     */
    void register(User user);

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
    List<Employee> getEmployeeList(Map<String,String> param);

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

    List<Dept> getDeptList(String pageNo,String pageSize);

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
    void removeJob(int id);

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
     * 获取所有公告
     * @return Notice List
     */
    List<Notice> findAllNotice();

    /**
     * 获取Notice的条目数
     * @return Notice的条目数
     */
    int getNoticesCount(Map<String, String> param);
    /**
     * 需要提供key 为 pageNo 和 pageSize 的数据
     */
    List<Notice> getNoticeList(Map<String,Object> param);

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