import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * ⼀一个RMI Server分为三部分：
 * 1. 一个继承了了java.rmi.Remote的接⼝，其中定义我们要远程调⽤用的函数，⽐比如这⾥里里的hello()
 * 2. 一个实现了了此接⼝的类
 * 3. 一个主类，⽤用来创建Registry，并将上⾯面的类实例例化后绑定到⼀个地址。这就是我们所谓的Server。
 *
 * 这⾥里里写的就⽐比较简单，三个东⻄放在一个类⾥了
 */
public class RMIServer {

    public interface IRemoteHelloWorld extends Remote {
        public String hello() throws RemoteException;
    }

    public static class RemoteHelloWorld extends UnicastRemoteObject implements IRemoteHelloWorld {
        protected RemoteHelloWorld() throws RemoteException {
            super();
        }
        public String hello() throws RemoteException {
            System.out.println("call from");
            return "Hello world";
        }
    }

    private void start() throws Exception {
        RemoteHelloWorld h = new RemoteHelloWorld();
        LocateRegistry.createRegistry(1099);
        // 格式rmi://host:port/name。
        // 其中，host和port就是RMI Registry的地址和端口，name是远程对象的名字
        // 如果RMI Registry在本地运行，那么host和port是可以省略的，
        // 此时host默认是localhost，port默认是1099
        Naming.rebind("rmi://127.0.0.1:1099/Hello", h);
    }


    public static void main(String[] args) throws Exception {
        new RMIServer().start();

    }

}
