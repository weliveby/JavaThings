package com.govuln.serialization_my.test;

import java.io.*;

public class Main {
    /**
     * 让GPT写的，为了测试readObject的递归调用，递归调用的逻辑存在于defaultReadObject
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 创建对象 B
        D d = new D(123);

        C c = new C(d);

        B b = new B(c);

        // 创建对象 A，包含对象 B
        A a = new A(b);

        // 序列化对象 A
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.ser"));
        oos.writeObject(a);
        oos.close();

        // 反序列化对象 A
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.ser"));
        A deserializedA = (A) ois.readObject();
        ois.close();
    }
}