package xyz.n490808114.train.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import xyz.n490808114.train.dto.JsonUserSerialize;

import javax.validation.constraints.Null;
@JsonInclude(Include.NON_NULL)
public class Notice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Null private Integer id;
    @Size(min = 1,max = 100)
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    @JsonUnwrapped
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
                        "','"+createDate
                        +"]";
    }
}
