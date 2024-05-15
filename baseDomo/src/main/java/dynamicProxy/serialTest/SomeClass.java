package dynamicProxy.serialTest;

import java.io.IOException;
import java.io.Serializable;

// 实现接口的具体类
class SomeClass implements SomeInterface, Serializable {
    public void doSomething() {
        System.out.println("Doing something...");
    }

    private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        System.out.println("aaaaaaaaaaaaaaaaaaa");
    }
}