package xyz.n490808114.service;


import xyz.n490808114.domain.*;

import java.util.List;

public interface HrmService {
    /**
     * 用户登录
     * @param loginName
     * @param password
     * @return User对象
     */
    User login(String loginName,String password);

    /**
     * 根据id查询用户
     * @param id
     * @return User对象
     */
    User findUserById(int id);

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
    void addUser(User user);

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

    /**
     * 修改员工
     * @param employee
     */
    void modifyEmployee(Employee employee);

    /**
     * 获取所有部门
     * @return Dept List
     */
    List<Dept> findAllDept();

    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    Dept findDeptById(int id);

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

