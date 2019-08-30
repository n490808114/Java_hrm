package xyz.n490808114.test.jacksonTest;

public class AccountBean{
    private Integer id;
    private String name;
    private String email;
    private String address;
    private Birthday birthday;

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Birthday birthday) {
        this.birthday = birthday;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }
    /**
     * @return the birthday
     */
    public Birthday getBirthday() {
        return birthday;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
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
    @Override
    public String toString() {
        return this.name + "#" 
                + this.id + "#" 
                + this.address + "#" 
                + this.birthday + "#" 
                + this.email;
    }
}