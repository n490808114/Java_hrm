package xyz.n490808114.train.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import xyz.n490808114.train.domain.Dept;
import xyz.n490808114.train.domain.Job;
import xyz.n490808114.train.domain.Sex;

@JsonInclude(Include.NON_NULL)
public class TitleDto {
    private String title;
    private Map<String,String> dataTitle;
    private Map<String,Sex> sexData;
    private Map<String,Dept> deptData;
    private Map<String,Job> jobData;
    public TitleDto(){}
    /**
     * @param dataTitle the dataTitle to set
     */
    public TitleDto setDataTitle(Map<String, String> dataTitle) {
        this.dataTitle = dataTitle;return this;
    }
    /**
     * @param deptData the deptData to set
     */
    public TitleDto setDeptData(Map<String, Dept> deptData) {
        this.deptData = deptData;return this;
    }
    /**
     * @param jobData the jobData to set
     */
    public TitleDto setJobData(Map<String, Job> jobData) {
        this.jobData = jobData;return this;
    }
    /**
     * @param sexData the sexData to set
     */
    public TitleDto setSexData(Map<String, Sex> sexData) {
        this.sexData = sexData;return this;
    }
    /**
     * @param title the title to set
     */
    public TitleDto setTitle(String title) {
        this.title = title;return this;
    }
    /**
     * @return the dataTitle
     */
    public Map<String, String> getDataTitle() {
        return dataTitle;
    }
    /**
     * @return the deptData
     */
    public Map<String, Dept> getDeptData() {
        return deptData;
    }
    /**
     * @return the jobData
     */
    public Map<String, Job> getJobData() {
        return jobData;
    }
    /**
     * @return the sexData
     */
    public Map<String, Sex> getSexData() {
        return sexData;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

}