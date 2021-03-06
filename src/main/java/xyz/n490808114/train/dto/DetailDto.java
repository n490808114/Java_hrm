package xyz.n490808114.train.dto;

import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = DetailSerializer.class)
public class DetailDto<T>{

    private Integer code;

    private String message;

    private String title;

    private Map<String,String> dataTitle;

    private T data;

    private Map<String,Map<String,? extends Object>> dataSelectMaps;

    public DetailDto(){}
    public DetailDto(Integer code,
                        String message,
                        String title,
                        Map<String,String> dataTitle,
                        T data){
        this.code = code;
        this.message =message;
        this.dataTitle = dataTitle;
        this.data = data;
    }


    public Builder builder(){return new Builder();}
    public class Builder{
        DetailDto<T> dto = new DetailDto<T>();
        public Builder code(Integer code){
            dto.setCode(code);
            return this;
        }
        public Builder message(String message){
            dto.setMessage(message);
            return this;
        }
        public Builder title(String title){
            dto.setTitle(title);
            return this;
        }
        public Builder dataTitle(Map<String,String> dataTitle){
            dto.setDataTitle(dataTitle);
            return this;
        }
        public Builder data(T o){
            dto.data = o;
            return this;
        }
        public Builder addDataMap(String key,Map<String,? extends Object> map){
            dto.addInDataMaps(key,map);
            return this;
        }
        public DetailDto<T> build(){return dto;}
    }


    public void addInDataMaps(String key,Map<String,? extends Object> map){
        if(this.dataSelectMaps== null){
            this.dataSelectMaps = new LinkedHashMap<>();
        }
        this.dataSelectMaps.put(key,map);
    }
    /**
     * @param dataSelectMaps the dataSelectMaps to set
     */
    public void setDataSelectMaps(Map<String, Map<String, ? extends Object>> dataSelectMaps) {
        this.dataSelectMaps = dataSelectMaps;
    }
    /**
     * @return the dataSelectMaps
     */
    public Map<String, Map<String, ? extends Object>> getDataSelectMaps() {
        return dataSelectMaps;
    }


    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }




    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param dataTitle the dataTitle to set
     */
    public void setDataTitle(Map<String, String> dataTitle) {
        this.dataTitle = dataTitle;
    }

    /**
     * @return the dataTitle
     */
    public Map<String, String> getDataTitle() {
        return dataTitle;
    }
    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }
    /**
     * @return the data
     */
    public T getData() {
        return data;
    }
}
