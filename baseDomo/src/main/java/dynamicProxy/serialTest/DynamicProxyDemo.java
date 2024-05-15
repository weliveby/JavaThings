package dynamicProxy.serialTest;



import java.io.*;
import java.lang.reflect.Proxy;

public class DynamicProxyDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 创建原始对象
        SomeInterface someObject = new SomeClass();
        
        // 创建代理对象
        SomeInterface proxy = (SomeInterface) Proxy.newProxyInstance(
            someObject.getClass().getClassLoader(),
            someObject.getClass().getInterfaces(),
            new MyInvocationHandler(someObject));

        // 序列化对象 A
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ab.ser"));
        oos.writeObject(proxy);
        oos.close();

        // 反序列化对象 A
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ab.ser"));
        SomeInterface deserializedA = (SomeInterface) ois.readObject();
        ois.close();
    }


}