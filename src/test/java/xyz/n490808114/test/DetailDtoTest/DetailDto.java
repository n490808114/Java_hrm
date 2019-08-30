package xyz.n490808114.test.DetailDtoTest;

import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class DetailDto{

    private Integer code;

    private String message;

    private String title;

    private Map<String,String> dataTitle;

    private Object data;

    private Map<String,Map<String,? extends Object>> dataMaps;

    public DetailDto(){}
    public DetailDto(Integer code,
                        String message,
                        String title,
                        Map<String,String> dataTitle,
                        Map<String,String> data){
        this.code = code;
        this.message =message;
        this.dataTitle = dataTitle;
        this.data = data;
    }


    public Builder builder(){return new Builder();}
    public class Builder{
        DetailDto dto = new DetailDto();
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
        public Builder data(Object o){
            dto.data = o;
            return this;
        }
        public Builder addDataMap(String key,Map<String,? extends Object> map){
            dto.addInDataMaps(key,map);
            return this;
        }
        public DetailDto build(){return dto;}
    }


    public void addInDataMaps(String key,Map<String,? extends Object> map){
        if(this.dataMaps == null){
            this.dataMaps = new LinkedHashMap<>();
        }
        this.dataMaps.put(key,map);
    }


    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @param dataMaps the dataMaps to set
     */
    public void setDataMaps(Map<String, Map<String,? extends Object>> dataMaps) {
        this.dataMaps = dataMaps;
    }

    /**
     * @return the dataMaps
     */
    public Map<String, Map<String,? extends Object>> getDataMaps() {
        return dataMaps;
    }



    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
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
     * @return the data
     */
    public Object getData() {
        return data;
    }
}
