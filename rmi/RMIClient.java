import java.rmi.Naming;

public class RMIClient {
    public static void main(String[] args) throws Exception {
        RMIServer.IRemoteHelloWorld hello = (RMIServer.IRemoteHelloWorld)
                Naming.lookup("rmi://192.168.135.142:1099/Hello");
        String ret = hello.hello();
        System.out.println(ret);

    }
}