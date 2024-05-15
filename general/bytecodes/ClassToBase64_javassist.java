import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.Base64;

public class ClassToBase64_javassist {
    public static void main(String[] args) throws NotFoundException, IOException, CannotCompileException {
        // javassist，字节码操纵的第三方库
        ClassPool pool = ClassPool.getDefault();
        CtClass clazz = pool.get(Hello.class.getName());
        byte[] bytes = clazz.toBytecode();
        //
        System.out.println(encodeToBase64(bytes));
    }

    private static String encodeToBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}
