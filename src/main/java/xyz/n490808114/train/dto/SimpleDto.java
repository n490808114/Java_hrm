package xyz.n490808114.train.dto;

public class SimpleDto{
    private Integer code;
    private Object message;
    public SimpleDto(){}
    public SimpleDto(Integer code,Object message){
        this.code = code;
        this.message = message;
    }
    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(Object message) {
        this.message = message;
    }
    /**
     * @return the message
     */
    public Object getMessage() {
        return message;
    }

}