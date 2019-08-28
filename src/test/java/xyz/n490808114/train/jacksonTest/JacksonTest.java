package xyz.n490808114.train.jacksonTest;

import java.io.IOException;
import java.nio.charset.Charset;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JacksonTest{
    private JsonGenerator jsonGenerator = null;
    private ObjectMapper objectMapper = null;
    private AccountBean bean = null;

    @Before
    public void init(){
        bean = new AccountBean();
        bean.setAddress("china-sichuan");
        bean.setEmail("123@qq.com");
        bean.setId(1);
        bean.setName("apaqi");

        objectMapper = new ObjectMapper();

        try{
            jsonGenerator = objectMapper.getFactory()
                                        .createGenerator(System.out, JsonEncoding.UTF8);                        
        }catch(IOException ex){ex.printStackTrace();}
    }
    @After
    public void destory(){
        try{
            if(jsonGenerator != null){
                jsonGenerator.flush();
            }
            if(!jsonGenerator.isClosed()){
                jsonGenerator.close();
            }
            jsonGenerator = null;
            objectMapper = null;
            bean = null;
            System.gc();
        }catch(IOException ex){ex.printStackTrace();}
    }
    @Test
    public void writeBeanJSON(){
        try{
            System.out.println("jsonGenerator:");
            jsonGenerator.writeObject(bean);
            System.out.println();

            System.out.println("obejctMapper:");
            objectMapper.writeValue(System.out, bean);
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    @Test
    public void writeOtherJSON(){
        try{
            String[] arr = {"a","b","c"};
            System.out.println("jsonGenerator:");
            String str = "abcdefghijklmn";
            jsonGenerator.writeBinary(str.getBytes(Charset.defaultCharset()));
            System.out.println("\n"+1);
            jsonGenerator.writeBoolean(true);
            System.out.println(2);
            jsonGenerator.writeNull();
            System.out.println(3);
            jsonGenerator.writeNumber(2.2f);
            System.out.println(4);
            jsonGenerator.writeRaw("c");
            System.out.println(5);
            jsonGenerator.writeRaw(str,5,5);
            System.out.println(6);
            jsonGenerator.writeRawValue(str,5,5);
            System.out.println(7);
            jsonGenerator.writeString(str);
            System.out.println(8);
            jsonGenerator.writeTree(JsonNodeFactory.instance.pojoNode(bean));
            System.out.println(9);
            jsonGenerator.writeStartObject();
            System.out.println(10);
            jsonGenerator.writeObjectFieldStart("user");
            System.out.println(11);
            jsonGenerator.writeStringField("name", "jackson");
            System.out.println(12);
            jsonGenerator.writeBooleanField("sex", true);
            System.out.println(13);
            jsonGenerator.writeNumberField("age", 22);
            System.out.println(14);
            jsonGenerator.writeEndObject();
            System.out.println(15);
            jsonGenerator.writeArrayFieldStart("infos");
            System.out.println(16);
            jsonGenerator.writeNumber(22);
            System.out.println(17);
            jsonGenerator.writeString("this is array");
            System.out.println(18);
            jsonGenerator.writeEndArray();
            System.out.println(19);
            jsonGenerator.writeEndObject();
            System.out.println(20);
            AccountBean bean = new AccountBean();
            bean.setAddress("address");
            bean.setEmail("email");
            bean.setId(1);
            bean.setName("name");
            System.out.println(21);
            jsonGenerator.writeStartObject();
            System.out.println(22);
            jsonGenerator.writeObjectField("user", bean);
            System.out.println(23);
            jsonGenerator.writeObjectField("infos", arr);
            System.out.println(24);
            jsonGenerator.writeEndObject();
            System.out.println(25);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    @Test
    public void writeObjectTest(){
        try{
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("error", 0);
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectFieldStart("data");
            jsonGenerator.writeStringField("name", "ABC");
            jsonGenerator.writeNumberField("age", 20);
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectFieldStart("phone");
            jsonGenerator.writeStringField("home", "abc");
            jsonGenerator.writeStringField("mobile", "def");
            jsonGenerator.writeEndObject();
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name", "DEF");
            jsonGenerator.writeObjectFieldStart("phone");
            jsonGenerator.writeStringField("home", "hij");
            jsonGenerator.writeStringField("phone", "klm");
            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndObject();
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectFieldStart("1");
            jsonGenerator.writeStringField("name", "GHI");
            jsonGenerator.writeObjectFieldStart("phone");
            jsonGenerator.writeStringField("home", "nop");
            jsonGenerator.writeStringField("phone", "qrs");
            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
            jsonGenerator.writeObjectFieldStart("other");
            jsonGenerator.writeArrayFieldStart("nickname");
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndObject();

        }catch(Exception ex){ex.printStackTrace();}
    }

}