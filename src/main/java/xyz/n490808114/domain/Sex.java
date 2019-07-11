package xyz.n490808114.domain;

public enum Sex{
    FEMALE("女",0),MALE("男",1),UNKNOW("未知",2);
    private String data;
    private int index;
    private Sex(String s,int index){
        this.data = s;
        this.index = index;
    }
    public void setData(String data) {this.data = data;}
    public String getData(){ return data;}
    public void setIndex(int index){this.index = index;}
    public int getIndex(){return index;}
}