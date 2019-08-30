package xyz.n490808114.train.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.n490808114.train.dao.*;
import xyz.n490808114.train.domain.*;
import xyz.n490808114.train.dto.ListDto;
import xyz.n490808114.train.service.HrmService;
import xyz.n490808114.train.util.TableTitle;

import java.util.*;

@Service("hrmServiceImpl")
public class HrmServiceImpl implements HrmService{
    private  static Log log = LogFactory.getLog(HrmServiceImpl.class);
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
    public ListDto<Notice> getNoticeList(Map<String,String> param){
        ListDto<Notice> dto = new ListDto<>();
        dto.setTitle("notice");
        dto.setData(noticeDao.getNoticeList(param));
        if(dto.getData().size() == 0){
            dto.setCode(404);
            dto.setMessage("找不到任何的公告");
            dto.setDataTitle(TableTitle.NOTICE_LIST_TITLE);
        }else{
            dto.setCode(200);
            dto.setMessage("获取成功");
            dto.setDataTitle(TableTitle.NOTICE_LIST_TITLE);
            dto.setCount(noticeDao.getCount(param));
            dto.setPageNo(Integer.parseInt(param.get("pageNo")));
            dto.setPageSize(Integer.parseInt(param.get("pageSize")));
            dto.setTitle("notice");
        }
        return dto;
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


    /**
     * @return the deptDao
     */
    public DeptDao getDeptDao() {
        return deptDao;
    }
    /**
     * @return the documentDao
     */
    public DocumentDao getDocumentDao() {
        return documentDao;
    }
    /**
     * @return the employeeDao
     */
    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }
    /**
     * @return the jobDao
     */
    public JobDao getJobDao() {
        return jobDao;
    }
    /**
     * @return the noticeDao
     */
    public NoticeDao getNoticeDao() {
        return noticeDao;
    }

    /**
     * @param deptDao the deptDao to set
     */
    public void setDeptDao(DeptDao deptDao) {
        this.deptDao = deptDao;
    }
    /**
     * @param documentDao the documentDao to set
     */
    public void setDocumentDao(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }
    /**
     * @param employeeDao the employeeDao to set
     */
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
    /**
     * @param jobDao the jobDao to set
     */
    public void setJobDao(JobDao jobDao) {
        this.jobDao = jobDao;
    }
    /**
     * @param noticeDao the noticeDao to set
     */
    public void setNoticeDao(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }

    
}
