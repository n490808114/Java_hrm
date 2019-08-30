package xyz.n490808114.test.DetailDtoTest;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import xyz.n490808114.train.domain.Dept;

public class DeptDetailSerializer extends JsonSerializer<Dept> {

    @Override
    public void serialize(Dept value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("name", value.getName());
        gen.writeStringField("remark", value.getRemark());
        gen.writeEndObject();
    }
    
}