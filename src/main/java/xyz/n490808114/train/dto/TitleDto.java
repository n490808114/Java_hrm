package xyz.n490808114.train.dto;

import java.util.Map;

public class TitleDto {
    private String title;
    private Map<String,String> dataTitle;
    public TitleDto(){}
    public TitleDto(String title,Map<String,String> dataTitle){
        this.title = title;
        this.dataTitle = dataTitle;
    }

    /**
     * @param dataTitle the dataTitle to set
     */
    public void setDataTitle(Map<String, String> dataTitle) {
        this.dataTitle = dataTitle;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return the dataTitle
     */
    public Map<String, String> getDataTitle() {
        return dataTitle;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
}