package xyz.n490808114.train.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.n490808114.train.dao.*;
import xyz.n490808114.train.domain.*;
import xyz.n490808114.train.service.HrmService;
import java.util.*;

@Service("hrmServiceImpl")
public class HrmServiceImpl implements HrmService{
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

    @Override
    public User login(String loginName,String password){
        return userDao.selectByLoginNameAndPassword(loginName,password);
    }

    @Override
    public User loginByEmail(String email, String password) {
        return userDao.selectByEmailAndPassword(email,password);
    }

    @Override
    public int registerCheckName(String name){
        return userDao.registerCheckName(name);
    }

    @Override
    public int registerCheckEmail(String email) {
        return userDao.registerCheckEmail(email);
    }

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
    public void register(User user){
        userDao.save(user);
    }
    @Override
    public Employee findEmployeeById(int id){
        return employeeDao.selectById(id);
    }
    @Override
    public int countEmployeesByDeptId(int deptId) {
        return employeeDao.getCountByDeptId(deptId);
    }
    @Override
    public int countEmployeesByJobId(int jobId) {
        return employeeDao.getCountByJobId(jobId);
    }

    @Override
    public List<Employee> getEmployeeList(Map<String,String> param){
        return employeeDao.selectEmployeeListWithParam(param);
    }

    @Override
    public int getEmployeeCount(Map<String, String> param) {
        return employeeDao.getCount(param);
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

    @Override
    public List<Dept> findAllDept(){
        return deptDao.selectAll();
    }

    @Override
    public List<Dept> getDeptList(String pageNo,String pageSize){
        return deptDao.getDeptList(pageNo,pageSize);
    }

    @Override
    public int getDeptCount() {
        return deptDao.getCount();
    }

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
        BeanDataCache.setDeptCacheExpired();
    }


    @Override
    public void addDept(Dept dept){
        deptDao.save(dept);
        BeanDataCache.setDeptCacheExpired();
    }


    @Override
    public void modifyDept(Dept dept){
        deptDao.modify(dept);
        BeanDataCache.setDeptCacheExpired();
    }

    @Override
    public List<Job> findAllJob(){
        return jobDao.selectAll();
    }

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
        BeanDataCache.setJobCacheExpired();
    }


    @Override
    public void removeJob(int id){
        jobDao.deleteById(id);
        BeanDataCache.setJobCacheExpired();
    }


    @Override
    public void modifyJob(Job job){
        jobDao.modify(job);
        BeanDataCache.setJobCacheExpired();
    }


    @Override
    public List<Document> findAllDocument(){
        return documentDao.selectAll();
    }


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


    @Override
    public List<Notice> findAllNotice(){
        return noticeDao.selectAll();
    }

    @Override
    public int getNoticesCount(Map<String, String> param) {
        return noticeDao.getCount(param);
    }


    @Override
    public List<Notice> getNoticeList(Map<String,Object> param){
        return noticeDao.getNoticeList(param);
    }


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
