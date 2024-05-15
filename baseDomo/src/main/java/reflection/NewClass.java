package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class NewClass {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        test0();
    }

    /**
     * newInstance()构造，也就是调用无参构造方法,前提：类有无参构造方法
     */
    public static void test0() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<?> aClass = Class.forName("java.lang.String");
        String s = (String)aClass.newInstance();
        System.out.println(s.length());
    }


    /**
     * 单例模式静态方法构造
     */
    public static void test1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //想要构造Runtime对象不能用newInstance，因为它没有公开的无参构造方法，只能用它自己提供的getRuntime()
        //getRuntime()属于单例模式中的静态方法，静态方法 类就能调用，而不需要实例对象。

        Class<?> clazz = Class.forName("java.lang.Runtime");
        Method method1 = clazz.getMethod("getRuntime");
        Method method2 = clazz.getMethod("exec",String.class);
        method2.invoke(method1.invoke(clazz),"calc");
    }

    /**
     * 有参构造方法构造
     */
    public static void test2() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName("java.lang.ProcessBuilder");
        Constructor<?> constructor = clazz.getConstructor(List.class);
        Method method = clazz.getMethod("start");
        method.invoke(constructor.newInstance(Arrays.asList("calc.exe")));
    }

    /**
     * 有参构造方法构造(换了另一个构造方法)
     */
    public static void test21() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName("java.lang.ProcessBuilder");
        // 原构造方法参数是(String... command),属于可变长参数，
        // 对于反射来说，如果要获取的目标函数里包含可变长参数，其实我们认为它是数组就行了。
        Constructor<?> constructor = clazz.getConstructor(String[].class);
        Method method = clazz.getMethod("start");
        method.invoke(constructor.newInstance(new String[][]{{"calc.exe"}}));
    }


    /**
     * getDeclaredConstructor
     * getDeclaredMethod系列方法获取的是当前类中“声明”的方法，是实在写在这个类里的，包括私有的方法，但从父类里继承来的就不包含了
     * 通过setAccessible修改，使得可以访问私有方法
     */
    public static void private1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName("java.lang.Runtime");
        Constructor<?> m = clazz.getDeclaredConstructor();
        m.setAccessible(true);
        clazz.getMethod("exec", String.class).invoke(m.newInstance(), "calc.exe");
    }

}
