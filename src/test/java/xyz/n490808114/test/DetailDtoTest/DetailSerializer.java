package xyz.n490808114.test.DetailDtoTest;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import xyz.n490808114.train.domain.Dept;
import xyz.n490808114.train.domain.Employee;

public class DetailSerializer extends JsonSerializer<DetailDto> {

	@Override
	public void serialize(DetailDto value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
            gen.writeNumberField("code", value.getCode());;
            gen.writeStringField("message", value.getMessage());
            gen.writeStringField("title", value.getTitle());
            gen.writeObjectField("dataTitle", value.getDataTitle());
            ObjectMapper objectMapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer(Dept.class, new DeptDetailSerializer());
            module.addSerializer(Employee.class, new EmployeeDetailSerializer());
            objectMapper.registerModule(module);
            gen.writeFieldName("data");
            objectMapper.writeValue(gen,value.getData());
            for(String index : value.getDataMaps().keySet()){
                gen.writeObjectFieldStart(index);
                for(String key:value.getDataMaps().get(index).keySet()){
                    gen.writeFieldName(key);
                    objectMapper.writeValue(gen,value.getDataMaps().get(index).get(key));
                }
                gen.writeEndObject();
                
            }
        gen.writeEndObject();
    }
    
}