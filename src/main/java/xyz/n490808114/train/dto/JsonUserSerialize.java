package xyz.n490808114.train.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import xyz.n490808114.train.domain.User;

public class JsonUserSerialize extends JsonSerializer<User> {

	@Override
	public void serialize(User value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(value.getUserName());
	}

}