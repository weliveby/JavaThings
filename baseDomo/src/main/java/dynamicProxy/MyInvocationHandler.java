package dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// 实现InvocationHandler接口
class MyInvocationHandler implements InvocationHandler {
    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在方法调用前做一些操作
        System.out.println("Before invoking method: " + method.getName());
        
        // 调用原始对象的方法
        Object result = method.invoke(target, args);
        
        // 在方法调用后做一些操作
        System.out.println("After invoking method: " + method.getName());
        
        return result;
    }
}