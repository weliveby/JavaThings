package com.govuln.deserialization_my.InstantiateTransformerDemo;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.InstantiateTransformer;

public class InstantiateTransformerExample {
    public static void main(String[] args) {
        // 构造函数参数类型数组
        Class<?>[] paramTypes = {String.class, int.class};

        // 构造函数参数值数组
        Object[] argsArray = {"Alice", 30};

        // 使用 new InstantiateTransformer 创建 Transformer 对象
        Transformer transformer = new InstantiateTransformer(paramTypes, argsArray);

        // 使用 Transformer 对象进行实例化
        Person person = (Person) transformer.transform(Person.class);

        // 输出实例化后的对象
        System.out.println("Instantiated person object: " + person);
    }
}
