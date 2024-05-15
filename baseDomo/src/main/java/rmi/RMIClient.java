package rmi;

import java.rmi.Naming;

public class RMIClient {
    public static void main(String[] args) throws Exception {
        // 使⽤用Naming.lookup在Registry中寻找到名字是Hello的对象
        RMIServer.IRemoteHelloWorld hello = (RMIServer.IRemoteHelloWorld)
                Naming.lookup("rmi://192.168.135.142:1099/Hello");
        String ret = hello.hello();
        System.out.println(ret);

    }
}