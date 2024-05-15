import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * 这个是chatgpt写的
 */

public class ClassToBase64 {

    public static void main(String[] args) {
        //如果不知道这里的filePath怎么写，可以打开下面的注释看下工作目录
        String filePath = "general/target/classes/HelloTemplatesImpl.class"; // 替换为你的class文件路径

        try {
            // 读取class文件内容到字节数组
            byte[] classBytes = readClassFile(filePath);
            // 将字节数组转换为Base64字符串
            String base64String = encodeToBase64(classBytes);

            System.out.println("Base64 encoded string: " + base64String);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static byte[] readClassFile(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            //从输入流 fis 中读取数据到 buffer 数组中，直到读取到末尾（read() 方法返回 -1）为止
            while ((bytesRead = fis.read(buffer)) != -1) {
                // 将buffer的数据写入到字节数组输出流 bos 中，第一个字节开始，写入 bytesRead 个字节的数据
                bos.write(buffer, 0, bytesRead);
            }

            return bos.toByteArray();
        }
    }

    private static String encodeToBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }


    //    // 程序运行时的工作目录
//     public static void main(String[] args) {
//        String workingDir = System.getProperty("user.dir");
//        System.out.println("Working Directory = " + workingDir);
//    }
}
