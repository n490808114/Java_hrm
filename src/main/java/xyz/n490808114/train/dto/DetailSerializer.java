package xyz.n490808114.train.dto;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import xyz.n490808114.train.domain.Dept;
import xyz.n490808114.train.domain.Employee;
import xyz.n490808114.train.domain.Job;
import xyz.n490808114.train.domain.Notice;

public class DetailSerializer<T> extends JsonSerializer<DetailDto<T>> {

	@Override
	public void serialize(DetailDto<T> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
            gen.writeNumberField("code", value.getCode());;
            gen.writeStringField("message", value.getMessage());
            gen.writeStringField("title", value.getTitle());
            gen.writeObjectField("dataTitle", value.getDataTitle());

            ObjectMapper detailMapper = new ObjectMapper();
            SimpleModule detailModule = new SimpleModule();
            detailModule.addSerializer(Dept.class, new DeptDetailSerializer());
            detailModule.addSerializer(Employee.class, new EmployeeDetailSerializer());
            detailModule.addSerializer(Job.class, new JobDetailSerializer());
            detailModule.addSerializer(Notice.class, new NoticeDetailSerializer());
            detailMapper.registerModule(detailModule);
            gen.writeFieldName("data");
            detailMapper.writeValue(gen,value.getData());

            ObjectMapper selectMapper = new ObjectMapper();
            SimpleModule selectModule = new SimpleModule();
            selectModule.addSerializer(Dept.class, new DeptSelectSerializer());
            selectModule.addSerializer(Job.class, new JobSelectSerializer());
            selectMapper.registerModule(selectModule);

            for(String index : value.getDataSelectMaps().keySet()){
                gen.writeFieldName(index);
                selectMapper.writeValue(gen,value.getDataSelectMaps().get(index));
            }
        gen.writeEndObject();
    }
    class NoticeDetailSerializer extends JsonSerializer<Notice>{

        @Override
        public void serialize(Notice value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeNumberField("id", value.getId());
            gen.writeStringField("title", value.getTitle());
            gen.writeStringField("content", value.getContent());
            gen.writeStringField("user", value.getUser().getName());
            gen.writeEndObject();
        }
        
    }
    class EmployeeDetailSerializer extends JsonSerializer<Employee>{

        @Override
        public void serialize(Employee value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("name", value.getName());
            gen.writeNumberField("dept", value.getDept().getId());
            gen.writeNumberField("job", value.getJob().getId());
            gen.writeStringField("cardId", value.getCardId());
            gen.writeStringField("address", value.getAddress());
            gen.writeStringField("postCode", value.getPostCode());
            gen.writeStringField("tel", value.getTel());
            gen.writeStringField("phone", value.getPhone());
            gen.writeStringField("qqNum", value.getQqNum());
            gen.writeStringField("email", value.getEmail());
            gen.writeNumberField("sex", value.getSex());
            gen.writeStringField("party", value.getParty());
            gen.writeStringField("birthday",value.getBirthday() ==null?"":new SimpleDateFormat("yyyy-MM-dd").format(value.getBirthday()));
            gen.writeStringField("race", value.getRace());
            gen.writeStringField("education", value.getEducation());
            gen.writeStringField("speciality", value.getSpeciality());
            gen.writeStringField("hobby", value.getHobby());
            gen.writeStringField("remark", value.getRemark());
            gen.writeEndObject();
        }
        
    }
    class DeptDetailSerializer extends JsonSerializer<Dept>{

        @Override
        public void serialize(Dept value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("name", value.getName());
            gen.writeStringField("remark", value.getRemark());
            gen.writeEndObject();
        }
        
    }
    class JobDetailSerializer extends JsonSerializer<Job>{

        @Override
        public void serialize(Job value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("name", value.getName());
            gen.writeStringField("remark", value.getRemark());
            gen.writeEndObject();
        }
        
    }
    class DeptSelectSerializer extends JsonSerializer<Dept>{

        @Override
        public void serialize(Dept value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("name", value.getName());
            gen.writeEndObject();
        }
        
    }
    class JobSelectSerializer extends JsonSerializer<Job>{

        @Override
        public void serialize(Job value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("name", value.getName());
            gen.writeEndObject();
        }
    }

}