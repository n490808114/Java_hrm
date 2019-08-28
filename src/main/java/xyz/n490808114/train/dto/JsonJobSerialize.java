package xyz.n490808114.train.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import xyz.n490808114.train.domain.Job;

public class JsonJobSerialize extends JsonSerializer<Job> {

	@Override
	public void serialize(Job value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(value.getName());
	}
    
}