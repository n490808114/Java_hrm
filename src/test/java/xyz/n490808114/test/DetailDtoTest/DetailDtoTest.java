package xyz.n490808114.test.DetailDtoTest;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import xyz.n490808114.train.domain.*;
import xyz.n490808114.train.util.TableTitle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.junit.Test;

public class DetailDtoTest{

    @Test
    public void testDetailDto(){
        Dept dept = new Dept(1, "部门", "这里是部门介绍");
        Job job = new Job(2,"职位","这里是职位介绍");
        Employee employee = new Employee(3, 
                                        dept, 
                                        job, 
                                        "员工", 
                                        "111111111111111111", 
                                        "address", 
                                        "postCode", 
                                        "tel", 
                                        "phone", 
                                        "qqNum", 
                                        "email", 
                                        1, 
                                        "party", 
                                        new Date(),
                                        "race", 
                                        "education", 
                                        "speciality", 
                                        "hobby", 
                                        "remark", 
                                        
                                        new Date());
        Map<String,Dept> deptMap = new LinkedHashMap<>();
        deptMap.put(dept.getId()+"", dept);
        DetailDto dto = new DetailDto().builder().code(200)
                                                .message("message conent")
                                                .data(employee)
                                                .title("employee")
                                                .dataTitle(TableTitle.EMPLOYEE_TITLE)
                                                .addDataMap("sexData", TableTitle.SEX_MAP)
                                                .addDataMap("deptData", deptMap)
                                                .build();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(DetailDto.class, new DetailSerializer());
        mapper.registerModule(module);
        String str = "";
        try{
            str = mapper.writeValueAsString(dto);
        }catch(Exception ex){ex.printStackTrace();}
        System.out.println(str);
    }
    @Test
    public void testEmployee(){
        Dept dept = new Dept(1, "部门", "这里是部门介绍");
        Job job = new Job(2,"职位","这里是职位介绍");
        Employee employee = new Employee(3, 
                                    dept, 
                                    job, 
                                    "员工", 
                                    "111111111111111111", 
                                    "address", 
                                    "postCode", 
                                    "tel", 
                                    "phone", 
                                    "qqNum", 
                                    "email", 
                                    1, 
                                    "party", 
                                    new Date(),
                                    "race", 
                                    "education", 
                                    "speciality", 
                                    "hobby", 
                                    "remark", 
                                    new Date()
        );
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Employee.class, new EmployeeDetailSerializer());
        mapper.registerModule(module);
        String str = "";
        try{
            str = mapper.writeValueAsString(employee);
        }catch(Exception ex){ex.printStackTrace();}
        System.out.println(str);
    }
}