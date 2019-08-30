package xyz.n490808114.train.dto;

import java.util.List;
import java.util.Map;

public class ListDto <T> extends SimpleDto{
    private String title;

    private Integer pageNo;
    private Integer pageSize;
    private Integer count;

    private Map<String,String> dataTitle;
    private Map<Integer,T> data;

    public ListDto(){

    }
    public ListDto(Integer code,Object message){
        this.code = code;
        this.message = message;
    }

    public ListDto(Integer code,Object message,String title,Integer pageNo,Integer pageSize,
                        Integer count,Map<String,String> dataTitle,Map<Integer,T> data)
    {
        this.code = code;
        this.message = message;
        this.title = title;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.count = count;
        this.dataTitle = dataTitle;
        this.data = data;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }


    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }



    /**
     * @return the pageNo
     */
    public Integer getPageNo() {
        return pageNo;
    }
    /**
     * @return the pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    @Override
    public void setCode(Integer code) {
        super.setCode(code);
    }
    @Override
    public void setMessage(Object message) {
        super.setMessage(message);
    }

    @Override
    public Integer getCode() {
        return super.getCode();
    }
    @Override
    public Object getMessage() {
        return super.getMessage();
    }
    /**
     * @param data the data to set
     */
    public void setData(Map<Integer, T> data) {
        this.data = data;
    }
    /**
     * @param dataTitle the dataTitle to set
     */
    public void setDataTitle(Map<String, String> dataTitle) {
        this.dataTitle = dataTitle;
    }
    /**
     * @return the data
     */
    public Map<Integer, T> getData() {
        return data;
    }
    /**
     * @return the dataTitle
     */
    public Map<String, String> getDataTitle() {
        return dataTitle;
    }
    
    

}