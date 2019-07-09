package xyz.n490808114.test;
public class BuilderTest{
    String name;
    int age;
    int high;
    int weight;
    int speed;
    public BuilderTest(Builder builder){
        this.name = builder.name;
        this.age = builder.age;
        this.high = builder.high;
        this.weight = builder.weight;
        this.speed = builder.speed;
    }
    public void setName(String name){this.name = name;}
    public void setAge(int age){this.age = age;}
    public void setHigh(int high){this.high = high;}
    public void setWeight(int weight){this.weight = weight;}
    public void setSpeed(int speed){this.speed = speed;}
    public String getName(){return this.name;}
    public int getAge(){return this.age;}
    public int getHigh(){return this.high;}
    public int getWeight(){return this.weight;}
    public int getSpeed(){return this.speed;}
    
    public static class Builder{
        String name;
        int age;
        int high;
        int weight;
        int speed;
        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder age(int age){
            this.age = age;
            return this;
        }
        public Builder high(int high){
            this.high = high;
            return this;
        }
        public Builder weight(int weight){
            this.weight = weight;
            return this;
        }
        public Builder speed(int speed){
            this.speed = speed;
            return this;
        }
        public BuilderTest build(){
            return new BuilderTest(this);
        }
    }
    public static void main(String[] args) {
        BuilderTest t = new BuilderTest.Builder().age(20).speed(50).build();
        BuilderTest s = new BuilderTest.Builder().age(120).speed(150).build();
        System.out.println(t.name);
        System.out.println(t.age);
        System.out.println(t.high);
        System.out.println(t.weight);
        System.out.println(t.speed);
        System.out.println(s.name);
        System.out.println(s.age);
        System.out.println(s.high);
        System.out.println(s.weight);
        System.out.println(s.speed);
    }
}