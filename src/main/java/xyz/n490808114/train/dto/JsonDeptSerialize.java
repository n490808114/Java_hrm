package xyz.n490808114.train.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import xyz.n490808114.train.domain.Dept;

public class JsonDeptSerialize extends JsonSerializer<Dept> {

	@Override
	public void serialize(Dept value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(value.getName());
	}
    
}