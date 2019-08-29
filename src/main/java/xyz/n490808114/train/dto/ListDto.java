package xyz.n490808114.train.dto;

import java.util.List;
import java.util.Map;

public class ListDto <T> extends SimpleDto{
    private String title;

    private Integer pageNo;
    private Integer pageSize;
    private Integer count;

    private Map<String,String> dataTitle;
    private List<T> data;

    public ListDto(){

    }
    public ListDto(Integer code,Object message){
        this.code = code;
        this.message = message;
    }

    public ListDto(Integer code,Object message,String title,Integer pageNo,Integer pageSize,
                        Integer count,Map<String,String> dataTitle,List<T> data)
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
     * @param data the data to set
     */
    /**
     * @param data the data to set
     */
    public void setData(List<T> data) {
        this.data = data;
    }
    /**
     * @param dataTitle the dataTitle to set
     */
    public void setDataTitle(Map<String, String> dataTitle) {
        this.dataTitle = dataTitle;
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
     * @return the dataTitle
     */
    public Map<String, String> getDataTitle() {
        return dataTitle;
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
    /**
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

}