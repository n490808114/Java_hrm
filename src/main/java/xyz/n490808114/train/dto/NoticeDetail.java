package xyz.n490808114.train.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import xyz.n490808114.train.domain.Notice;

@JsonIgnoreProperties({"createDate"})
public class NoticeDetail extends Notice {

    private static final long serialVersionUID = 1L;
    public NoticeDetail(){}
    public NoticeDetail(Notice notice){
        this.setId(notice.getId());
        this.setContent(notice.getContent());
        this.setCreateDate(notice.getCreateDate());
        this.setTitle(notice.getTitle());
        this.setUser(notice.getUser());
    }
}