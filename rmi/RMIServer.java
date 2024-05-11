import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class RMIServer {

    public interface IRemoteHelloWorld extends Remote {
        public String hello() throws RemoteException;
    }

    public class RemoteHelloWorld extends UnicastRemoteObject implements IRemoteHelloWorld {
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
