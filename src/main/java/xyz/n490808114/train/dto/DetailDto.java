package xyz.n490808114.train.dto;

import java.util.Map;

public class DetailDto<T> {
    private Integer code;
    private String message;

    private String title;

    private Map<String,String> dataTitle;
    private T data;

    public DetailDto(){}

    public DetailDto(Integer code,String message){
        this.code =code;
        this.message = message;
    }
    public DetailDto(Integer code,String message,String title,Map<String,String> dataTitle,T data){
        this.code = code;
        this.message = message;
        this.title = title;
        this.dataTitle = dataTitle;
        this.data = data;
    }
    
    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }
    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }
    /**
     * @param dataTitle the dataTitle to set
     */
    public void setDataTitle(Map<String, String> dataTitle) {
        this.dataTitle = dataTitle;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }
    /**
     * @return the data
     */
    public T getData() {
        return data;
    }
    /**
     * @return the dataTitle
     */
    public Map<String, String> getDataTitle() {
        return dataTitle;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
}