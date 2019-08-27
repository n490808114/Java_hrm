package xyz.n490808114.train.domain;

import java.io.Serializable;

public class Sex implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    public Sex(){

    }
    public Sex(int id,String name){
        this.id = id;
        this.name = name;
    }
    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
}