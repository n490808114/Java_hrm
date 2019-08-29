package xyz.n490808114.train.domain;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.List;



public class Job implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    @Null private Integer id;
    @Size(min=1,max=20) private String name;
    private String remark;
    @JsonIgnore
    @Null private List<Employee> employees;
    public Job(){}
    public Job(int id){
        this.id = id;
    }
    public Job(int id,String name){
        this.id = id;
        this.name = name;
    }
    public Job(int id,String name,String remark){
        this.id=id;
        this.name=name;
        this.remark=remark;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }


    public String getName() {
        return name;
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

    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return ""+id+" "+name+" "+remark;
    }

}
