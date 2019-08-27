package xyz.n490808114.train.domain;

import javax.validation.constraints.Size;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.List;


public class Dept implements Serializable {
    private static final long serialVersionUID = 1L;
    @Null private Integer id;
    @Size(min=1,max=10) private String name;
    private String remark;
    @Null private List<Employee> employees;
    public Dept(){}
    public Dept(int id){
        this.id = id;
    }
    public Dept(int id,String name){
        this.id = id;
        this.name = name;
    }
    public Dept(int id,String name,String remark){
        this.id=id;
        this.name=name;
        this.remark=remark;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public String toString() {
        return "Dept [id=" + id +
                    ",name=" +name +
                    ",remark=" + remark + "]";
    }
}
