package test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

class A implements Serializable {
    private static final long serialVersionUID = 1L;
    private B b;

    public A(B b) {
        this.b = b;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException, IOException {
        // 调用默认的反序列化方法，读取默认的序列化数据
        ois.defaultReadObject();
        // 添加自定义的反序列化逻辑
        System.out.println("Custom readObject() method of A is called.");
    }

    @Override
    public String toString() {
        return "A{" +
                "b=" + b +
                '}';
    }
}