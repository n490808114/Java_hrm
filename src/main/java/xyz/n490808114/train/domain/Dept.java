package xyz.n490808114.train.domain;

import java.io.Serializable;
import java.util.List;

public class Dept implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String remark;
    private List<Employee> employees;
    public Dept(){}
    public Dept(int id){
        this.id = id;
    }
    public Dept(int id,String name,String remark){
        this.id=id;
        this.name=name;
        this.remark=remark;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
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
