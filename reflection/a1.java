import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class a1 {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        private1();
    }

    /**
     * newInstance()构造，也就是调用无参构造方法
     */
    public static void test0(){

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

    public static void test21() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName("java.lang.ProcessBuilder");
        Constructor<?> constructor = clazz.getConstructor(String[].class);
        Method method = clazz.getMethod("start");
        method.invoke(constructor.newInstance(new String[][]{{"calc.exe"}}));
    }


    /**
     * 通过setAccessible修改，使得可以访问私有方法
     */
    public static void private1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName("java.lang.Runtime");
        Constructor<?> m = clazz.getDeclaredConstructor();
        m.setAccessible(true);
        clazz.getMethod("exec", String.class).invoke(m.newInstance(), "calc.exe");
    }

}
