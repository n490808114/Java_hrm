package xyz.n490808114.test;

public class Chinese2Unicode {
    public static void main(String[] args) {
        String a = "测试123String";
        char[] l = a.toCharArray();
        for(char c : l){
            System.out.println(Integer.toString(c,16));
        }
    }
}
