package xyz.n490808114.train.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import xyz.n490808114.train.domain.Dept;
import xyz.n490808114.train.domain.Employee;
import xyz.n490808114.train.domain.Job;
import xyz.n490808114.train.domain.Notice;;

@JsonInclude(Include.NON_NULL)
public class DetailDto extends SimpleDto {
    private String title;

    private Map<String,String> dataTitle;

    private Map<String,String> data;

    private List<Map<String,String>> dataMaps;

    public DetailDto(){}
    public DetailDto(Integer code,
                        Object message,
                        String title,
                        Map<String,String> dataTitle,
                        Map<String,String> data){
        this.code = code;
        this.message =message;
        this.dataTitle = dataTitle;
        this.data = data;
    }

    @Override
    public void setCode(Integer code) {
        super.setCode(code);
    }
    @Override
    public void setMessage(Object message) {
        super.setMessage(message);
    }
    public Builder builder(){return new Builder();}
    public class Builder{
        DetailDto dto = new DetailDto();
        public Builder code(Integer code){
            dto.setCode(code);
            return this;
        }
        public Builder message(Object message){
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
            if(o instanceof Notice){
                dto.setData(this.parseData((Notice) o));
            }else if(o instanceof Employee){
                dto.setData(this.parseData((Employee) o));
            }else if(o instanceof Dept){
                dto.setData(this.parseData((Dept) o));
            }else if(o instanceof Job){
                dto.setData(this.parseData((Job) o));
            }
            return this;
        }
        public Builder addDataMap(Map<String,? extends Object> map){
            Map<String,String> strMap = new LinkedHashMap<>();
            for(String str : map.keySet()){
                if(map.get(str) instanceof Dept){
                    strMap.put(str, ((Dept) map.get(str)).getName());
                }else if(map.get(str) instanceof Job){
                    strMap.put(str, ((Job) map.get(str)).getName());
                }else if(map.get(str) instanceof String){
                    strMap.put(str, (String) map.get(str));
                }
            }
            dto.addInDataMaps(strMap);
            return this;
        }
        public DetailDto build(){return dto;}
        public Map<String,String> parseData(Notice notice){
            Map<String,String> map = new LinkedHashMap<>();
            map.put("id",""+notice.getId());
            map.put("title",notice.getTitle());
            map.put("content",notice.getContent());
            map.put("user",notice.getUser().getUserName());
            return map;
        }
        public Map<String,String> parseData(Employee employee){
            Map<String,String> map = new LinkedHashMap<>();
            map.put("id",""+ employee.getId());
            map.put("name",employee.getName());
            map.put("sex",""+employee.getSex());
            map.put("dept", ""+ employee.getDept().getId());
            map.put("job",""+ employee.getJob().getId());
            map.put("cardId",employee.getCardId());
            map.put("address",employee.getAddress());
            map.put("postCode",employee.getPostCode());
            map.put("tel",employee.getTel());
            map.put("phone", employee.getPhone());
            map.put("qqNum",employee.getQqNum());
            map.put("email",employee.getEmail());
            map.put("party",employee.getParty());
            map.put("birthday",employee.getBirthday()==null?"":new SimpleDateFormat("yyyy-MM-dd").format(employee.getBirthday()));
            map.put("race",employee.getRace());
            map.put("education",employee.getEducation());
            map.put("speciality",employee.getSpeciality());
            map.put("hobby",employee.getHobby());
            map.put("remark",employee.getRemark());
            return map;
        }
        public Map<String,String> parseData(Dept dept){
            Map<String,String> map = new LinkedHashMap<>();
            map.put("name", dept.getName());
            map.put("remark", dept.getRemark());
            return map;
        }
        public Map<String,String> parseData(Job job){
            Map<String,String> map = new LinkedHashMap<>();
            map.put("name", job.getName());
            map.put("remark", job.getRemark());
            return map;
        }
    }


    public void addInDataMaps(Map<String,String> map){
        if(this.dataMaps == null){
            this.dataMaps = new ArrayList<>();
        }
        this.dataMaps.add(map);
    }


    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @param dataMaps the dataMaps to set
     */
    public void setDataMaps(List<Map<String, String>> dataMaps) {
        this.dataMaps = dataMaps;
    }
    /**
     * @return the dataMaps
     */
    public List<Map<String, String>> getDataMaps() {
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
    public void setData(Map<String, String> data) {
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
    public Map<String, String> getData() {
        return data;
    }
    /**
     * @return the dataTitle
     */
    public Map<String, String> getDataTitle() {
        return dataTitle;
    }
}
