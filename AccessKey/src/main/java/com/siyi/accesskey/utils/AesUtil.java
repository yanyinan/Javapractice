package com.siyi.accesskey.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesUtil {

    private static final String KEY = "aBc123$&&^ew12mM";
    private static final String IV = "xyz123$&&^ew12mM";

    public static String byteToString(byte[] byte1){
        return new String(byte1);
    }
    public static byte[] AES_CBC_Encrypt(byte[] content, byte[] keyBytes, byte[] iv){
        try {
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,key, new IvParameterSpec(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            System.out.println("exception:"+e.toString());
        }
        return null;
    }
    public static byte[] AES_CBC_Decrypt(byte[] content, byte[] keyBytes, byte[] iv){
        try {
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,key, new IvParameterSpec(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            System.out.println("exception:"+e.toString());
            return null;
        }
    }
    //字符串装换成base64
    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.decodeBase64(key.getBytes());
    }
    //base64装换成字符串
    public static String encryptBASE64(byte[] key) throws Exception {
        return new String(Base64.encodeBase64(key));
    }

    public static String AES_CBC_Encrypt_String(String content, String key, String iv) throws Exception {
        return encryptBASE64(AES_CBC_Encrypt(content.getBytes(), key.getBytes(), iv.getBytes()));
    }

    public static String AES_CBC_Decrypt_String(String content, String key, String iv) throws Exception {
        return new String(AES_CBC_Decrypt(decryptBASE64(content), key.getBytes(), iv.getBytes()));
    }

    public static String AES_CBC_Encrypt_String(String content) throws Exception {
        return AES_CBC_Encrypt_String(content, KEY, IV);
    }

    public static String AES_CBC_Decrypt_String(String content) throws Exception {
        return AES_CBC_Decrypt_String(content, KEY, IV);
    }

    public static void main(String[] args) throws Exception {
        String content = "123456";

        String en = AES_CBC_Encrypt_String(content);
        System.out.println(en);

        String s = AES_CBC_Decrypt_String(en);
        System.out.println(s);
    }
}
