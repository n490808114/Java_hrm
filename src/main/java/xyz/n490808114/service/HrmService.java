package xyz.n490808114.service;


import xyz.n490808114.domain.*;

import java.util.List;

public interface HrmService {
    /**
     * �û���¼
     * @param loginName
     * @param password
     * @return User����
     */
    User login(String loginName,String password);

    /**
     * ����id��ѯ�û�
     * @param id
     * @return User����
     */
    User findUserById(int id);

    /**
     * ����idɾ���û�
     * @param id
     */
    void removeUserById(int id);

    /**
     * �޸��û�
     * @param user
     */
    void modifyUser(User user);

    /**
     * ����û�
     * @param user
     */
    void addUser(User user);

    /**
     * ����id��ѯԱ��
     * @param id
     * @return
     */
    Employee findEmployeeById(int id);

    /**
     * ����idɾ��Ա��
     * @param id
     */
    void removeEmployeeById(int id);

    /**
     * ���Ա��
     * @param employee
     */
    void addEmployee(Employee employee);

    /**
     * �޸�Ա��
     * @param employee
     */
    void modifyEmployee(Employee employee);

    /**
     * ��ȡ���в���
     * @return Dept List
     */
    List<Dept> findAllDept();

    /**
     * ����id��ѯ����
     * @param id
     * @return
     */
    Dept findDeptById(int id);

    /**
     * ����idɾ������
     * @param id
     */
    void removeDeptById(int id);

    /**
     * ��Ӳ���
     * @param dept
     */
    void addDept(Dept dept);

    /**
     * �޸Ĳ���
     * @param dept
     */
    void modifyDept(Dept dept);

    /**
     * ��ȡ����ְλ
     * @return Job List
     */
    List<Job> findAllJob();

    /**
     * ����id��ѯְλ
     * @param id
     * @return
     */
    Job findJobById(int id);

    /**
     * ���ְλ
     * @param job
     */
    void addJob(Job job);

    /**
     * ����idɾ��ְλ
     * @param id
     */
    void removeJob(int id);

    /**
     * �޸�ְλ
     * @param job
     */
    void modifyJob(Job job);

    /**
     * ��ȡ�����ĵ�
     * @return Document List
     */
    List<Document> findAllDocument();

    /**
     * ����id��ѯ�ĵ�
     * @param id
     * @return Document ����
     */
    Document findDocumentById(int id);

    /**
     * ����idɾ���ĵ�
     * @param id
     */
    void removeDocument(int id);

    /**
     * ����ĵ�
     * @param document
     */
    void addDocument(Document document);

    /**
     * �޸��ĵ�
     * @param document
     */
    void modifyDocument(Document document);

    /**
     * ��ȡ���й���
     * @return Notice List
     */
    List<Notice> findAllNotice();

    /**
     * ����id��ѯ����
     * @param id
     * @return Notice����
     */
    Notice findNoticeById(int id);

    /**
     * ����idɾ������
     * @param id
     */
    void removeNotice(int id);

    /**
     * ��ӹ���
     * @param notice
     */
    void addNotice(Notice notice);

    /**
     * �޸Ĺ���
     * @param notice
     */
    void modifyNotice(Notice notice);
}

