package xyz.n490808114.train.service;

import org.springframework.stereotype.Service;
import xyz.n490808114.train.domain.*;
import xyz.n490808114.train.dto.ListDto;

import java.util.*;

@Service
public interface HrmService {

    Employee findEmployeeById(int id);

    void removeEmployeeById(int id);

    void addEmployee(Employee employee);

    void addEmployee(Map<String,String> param);

    ListDto<Employee> getEmployeeList(Map<String,String> param);

    int getEmployeeCount(Map<String, String> param);

    int countEmployeesByDeptId(int deptId);

    int countEmployeesByJobId(int jobId);

    void modifyEmployee(Employee employee);

    void modifyEmployee(Map<String,String> param);

    List<Dept> findAllDept();

    ListDto<Dept> getDeptList(Map<String,String> param);

    int getDeptCount();

    Dept findDeptById(int id);

    Dept findDeptByName(String name);

    void removeDeptById(int id);

    void addDept(Dept dept);

    void modifyDept(Dept dept);

    List<Job> findAllJob();

    ListDto<Job> getJobList(Map<String,String> param);

    Job findJobById(int id);

    Job findJobByName(String name);

    void addJob(Job job);

    void removeJobById(int id);

    void modifyJob(Job job);

    List<Document> findAllDocument();

    Document findDocumentById(int id);

    void removeDocument(int id);

    void addDocument(Document document);

    void modifyDocument(Document document);

    ListDto<Notice> getNoticeList(Map<String,String> param);

    Notice findNoticeById(int id);

    void removeNotice(int id);

    void addNotice(Notice notice);

    void modifyNotice(Notice notice);

    ListDto<Document> getDocumentList(Map<String, String> param);
}