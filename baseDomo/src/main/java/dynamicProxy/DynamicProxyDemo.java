package dynamicProxy;

import java.lang.reflect.Proxy;

public class DynamicProxyDemo {
    public static void main(String[] args) {
        // 创建原始对象
        SomeInterface someObject = new SomeClass();
        
        // 创建代理对象
        SomeInterface proxy = (SomeInterface) Proxy.newProxyInstance(
            someObject.getClass().getClassLoader(),
            someObject.getClass().getInterfaces(),
            new MyInvocationHandler(someObject));
        
        // 通过代理对象调用方法
        proxy.doSomething();
    }


}