package xyz.n490808114.domain;

import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {
    private int id;
    private String title;
    private String content;
    private Date createDate;
    private User user;

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
}
