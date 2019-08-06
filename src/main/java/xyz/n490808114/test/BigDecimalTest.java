package xyz.n490808114.test;

import java.math.BigDecimal;



public class BigDecimalTest{
    public static void main(String[] args) {

        BigDecimal b = new BigDecimal("100000000000000000000");
        System.out.println("100000000000000000000".length());
        BigDecimal c = b.divide(new BigDecimal("1000"));
        System.out.println(c.toString());
        System.out.println(c.toString().length());
        System.out.println(c.doubleValue());
    }
}