package xyz.n490808114.test.DetailDtoTest;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import xyz.n490808114.train.domain.Employee;

public class EmployeeDetailSerializer extends JsonSerializer<Employee> {

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
		gen.writeStringField("birthday", new SimpleDateFormat("yyyy-MM-dd").format(value.getBirthday()));
		gen.writeStringField("race", value.getRace());
		gen.writeStringField("education", value.getEducation());
		gen.writeStringField("speciality", value.getSpeciality());
		gen.writeStringField("hobby", value.getHobby());
		gen.writeStringField("remark", value.getRemark());
		gen.writeEndObject();
	}
    
}