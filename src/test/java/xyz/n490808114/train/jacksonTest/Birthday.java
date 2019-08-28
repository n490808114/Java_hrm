package xyz.n490808114.train.jacksonTest;

public class Birthday{
    private String birthday;
    public Birthday(String birthday){
        this.birthday = birthday;
    }
    public Birthday(){}

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    /**
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }
    @Override
    public String toString() {
        return this.birthday;
    }
}