package xyz.n490808114.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.n490808114.dao.*;
import xyz.n490808114.domain.*;

import java.util.*;

/**
 * ���¹���ϵͳ�����ӿ�ʵ����
 */
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
@Service("hrmServiceImpl")
public class HrmServiceImpl implements HrmService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private DeptDao deptDao;
    @Autowired
    private JobDao jobDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DocumentDao documentDao;
    @Autowired
    private NoticeDao noticeDao;

    @Transactional(readOnly = true)
    @Override
    public User login(String loginName,String password){
        return userDao.selectByLoginNameAndPassword(loginName,password);
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserById(int id){
        return userDao.selectById(id);
    }


    @Override
    public void removeUserById(int id){
        userDao.deleteById(id);
    }


    @Override
    public void modifyUser(User user){
        userDao.update(user);
    }


    @Override
    public void addUser(User user){
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Employee findEmployeeById(int id){
        return employeeDao.selectById(id);
    }

    @Override
    public List<Employee> getEmployeeList(Map<String,Object> param){
        return employeeDao.selectEmployeeListWithParam(param);
    }

    @Override
    public int getEmployeeCount() {
        return employeeDao.getCount();
    }

    @Override
    public void removeEmployeeById(int id){
        employeeDao.deleteById(id);
    }


    @Override
    public void addEmployee(Employee employee){
        employeeDao.save(employee);
    }

    public void addEmployee(Map<String,String> param){
        employeeDao.insert(param);
    }


    @Override
    public void modifyEmployee(Employee employee){
        employeeDao.modify(employee);
    }
    @Override
    public void modifyEmployee(Map<String, String> param) {
        employeeDao.modifyByParam(param);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Dept> findAllDept(){
        return deptDao.selectAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Dept findDeptById(int id){
        return deptDao.selectById(id);
    }
    @Override
    public Dept findDeptByName(String name){
        return deptDao.selectByName(name);
    }

    @Override
    public void removeDeptById(int id){
        deptDao.deleteById(id);
    }


    @Override
    public void addDept(Dept dept){
        deptDao.save(dept);
    }


    @Override
    public void modifyDept(Dept dept){
        deptDao.modify(dept);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Job> findAllJob(){
        return jobDao.selectAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Job findJobById(int id){
        return jobDao.selectById(id);
    }
    @Override
    public Job findJobByName(String name){
        return jobDao.selectByName(name);
    }

    @Override
    public void addJob(Job job){
        jobDao.save(job);
    }


    @Override
    public void removeJob(int id){
        jobDao.deleteById(id);
    }


    @Override
    public void modifyJob(Job job){
        jobDao.modify(job);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Document> findAllDocument(){
        return documentDao.selectAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Document findDocumentById(int id){
        return documentDao.selectById(id);
    }


    @Override
    public void removeDocument(int id){
        documentDao.deleteById(id);
    }


    @Override
    public void addDocument(Document document){
        documentDao.save(document);
    }


    @Override
    public void modifyDocument(Document document){
        documentDao.modify(document);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Notice> findAllNotice(){
        return noticeDao.selectAll();
    }

    @Override
    public int getNoticesCount() {
        return noticeDao.getCount();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Notice> getNoticeList(Map<String,Object> param){
        return noticeDao.getNoticeList(param);
    }

    @Transactional(readOnly = true)
    @Override
    public Notice findNoticeById(int id){
        return noticeDao.selectById(id);
    }


    @Override
    public void removeNotice(int id){
        noticeDao.deleteById(id);
    }


    @Override
    public void addNotice(Notice notice){
        noticeDao.save(notice);
    }


    @Override
    public void modifyNotice(Notice notice){
        noticeDao.modify(notice);
    }

}
