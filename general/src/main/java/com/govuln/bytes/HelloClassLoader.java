package com.govuln.bytes;

import java.net.URL;
import java.net.URLClassLoader;

public class HelloClassLoader
{
    public static void main( String[] args ) throws Exception
    {
        // 需要先放Hello.class在8000服务器目录下
        URL[] urls = {new URL("http://localhost:8000/")};
        URLClassLoader loader = URLClassLoader.newInstance(urls);
        Class c = loader.loadClass("Hello");
        c.newInstance();
    }
}
