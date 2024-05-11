import java.io.*;

public class Main {

    public static void main(String[] args) {
        testRead();
    }

    static void testWrite(){
        Person person = new Person("a",11);
        try {
            // 创建 ObjectOutputStream
            FileOutputStream fileOut = new FileOutputStream("object.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            // 调用 writeObject() 方法将对象写入输出流
            out.writeObject(person);

            // 关闭输出流
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in object.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void testRead(){
        try {
            // 创建 FileInputStream 用于从文件中读取数据
            FileInputStream fileIn = new FileInputStream("object.ser");

            // 创建 ObjectInputStream 用于从 FileInputStream 中读取对象
            ObjectInputStream in = new ObjectInputStream(fileIn);

            // 调用 readObject() 方法反序列化对象
            Person obj = (Person) in.readObject();

            // 关闭输入流
            in.close();
            fileIn.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
