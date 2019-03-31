package xyz.n490808114.domain;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

import java.io.Serializable;
import java.util.Date;

@JSONType(orders = {"id","title","content","createDate","user"})
public class Notice implements Serializable {
    private int id;
    private String title;
    private String content;
    @JSONField(format = "yyyy-mm-dd")
    private Date createDate;
    private User user;

    public Notice(){}
    public Notice(int id,String title,String content,Date createDate,User user){
        this.id=id;
        this.title=title;
        this.content=content;
        this.createDate=createDate;
        this.user=user;
    }
    public Notice(String title,String content,User user){
        this.title=title;
        this.content=content;
        this.user=user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Notice [" + id +
                        ",'"+title +
                        "',+'"+content+
                        "','"+createDate+
                        "',"+user.getUserName()+"]";
    }
}
