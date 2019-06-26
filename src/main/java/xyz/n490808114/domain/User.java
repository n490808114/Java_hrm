package xyz.n490808114.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int id;
    private String userName;
    private String loginName;
    private String password;
    private int status;
    private Date createDate;
    private String email;
    public User(){
        createDate = new Date();
    }
    public User(int id,String userName,String loginName,String password,Integer status,Date createDate,String email){
        this.id=id;
        this.userName=userName;
        this.loginName=loginName;
        this.password=password;
        this.status=status;
        this.createDate=createDate;
        this.email=email;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "User [id="+id+
                ",userName="+userName+
                ",loginName="+loginName+
                ",password="+password+
                ",status="+status+
                ",createDate="+createDate+
                ",email="+email+""+"]";
    }
}
