package xyz.n490808114.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MethodTest {
    public static void main(String[] args){
        TestInterface test = new Test();
        HandlerTest handlerTest = new HandlerTest(test);

        TestInterface proxy = (TestInterface) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                                                                    test.getClass().getInterfaces(),handlerTest);
        proxy.getName();
    }
}
class HandlerTest implements InvocationHandler{
    private Object target;
    public HandlerTest(Object target){this.target=target;}
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Handler Start!");
        Object result = method.invoke(target,args);
        System.out.println("Handler End!");
        return result;
    }
}
interface TestInterface{
    String getName();
}
class Test implements TestInterface{
    private String name = "testName";

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        System.out.println(name);
        return name;
    }
}