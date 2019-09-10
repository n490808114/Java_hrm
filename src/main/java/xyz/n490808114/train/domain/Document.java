package xyz.n490808114.train.domain;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class Document implements Serializable {
    private static final long serialVersionUID = 1L;
    @Null private Integer id;
    @Size(min = 1,message = "title不能为空") String title;
    private String fileName;
    private MultipartFile file;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Null private Date createDate;
    @JsonUnwrapped
    @Null private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getRemark() {
        return remark;
    }

    public MultipartFile getFile() {
        return file;
    }

    public String getFileName() {
        return fileName;
    }
}
