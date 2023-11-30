package com.siyi.accesskey.utils;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;

public class RSAUtil {

    public static final String RSA = "RSA";// 非对称加密密钥算法
    public static final String ECB_PADDING = "RSA/ECB/PKCS1Padding";// 加密填充方式

    private static final int KEY_SIZE = 2048;// 密钥位数
    private static final int RESERVE_BYTES = 11;
    private static final int DECRYPT_BLOCK = KEY_SIZE / 8;
    private static final int ENCRYPT_BLOCK = DECRYPT_BLOCK - RESERVE_BYTES;

    public static void main(String[] args) throws Exception {
        Map<Integer, String> map = genKeyPair();
        System.out.println("公钥："+map.get(0));
        System.out.println("私钥："+map.get(1));

        String publicKey = map.get(0);
        String privateKey = map.get(1);
        String password="a123456";
        String encryptPassword = RSAUtil.encryptWithPublicKeyBlock(password, publicKey);
        System.out.println("加密后：" + encryptPassword);
        System.out.println("解密后：" + decryptWithPrivateKeyBlock(encryptPassword, privateKey));

        //String s1 ="Su5L5hSJhMXhvoH+7nvxri5a5AXmcWNBXmsMEAkKAbWfYiNCryAjuGdO62AZZQ2S7Kj1idbH9Ju10sMEsNV1WvmKja5wOXOiKRH0IjK2vtpI3emT6qn1UA6DeGnMtZXqFYeJY+gFIHkpM6QRkCHuFaMtWU0ydED/GLBcQgxaaVbmvPvMktQi692Uf5mgb7jPMA1sQiy1Zf/cpQpJ98RzsTZk7sc5qqbXfOmdYwCcwXhy0kQ2NMRM/m0m3+OD5LMfit3ecKs7eMeQyoth47GN3yZHzpEqo6sQYolzFoetWugn1ElM0HQiXj0JNW8EDNaaoEYcGystc4T0DSlcVqLN0w==";
        //privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDb2OPjuGEmLaQTmPlWJUhXAeVJYGzwW/JEPK3RDAbPLidp7S5+fKgwyxpqWcuPn/hLnmDgr1a66Ek96AEWxpWUAlaRo4R2bwAvi0Dlb45mVGuhddb8pWEeu0XpEAUUUBjh+rtPsQGjl6OYRWfECNSE+eQ/Yxfha9Q+T09nmY+k+HD9bZQfLrw6tmkuRIp7B6dNRqk48fMosQPeyboankIhSHEiHG48ITTOlLuQG7d1WZdrJ0vWVwDy2/rAMxpIvCIh4ZtlSw8361Z8swkWDUBs1eByTeI29/Wdv1d+Ui+39HjK+iJoXu4iIXFdsjBww2UpaR/f7RnjW5u9+jpPlwl5AgMBAAECggEAElzhXAnwg2HYjJKAcQOYMTxFTIWzU7g6Why/X/CjOPbf7+cRtRrwaXLtd+icKhjwglBuQxYiJmZ0h6kzPhXENKKlXHDOBZ5r44y8FO3mdQGkI85V8LBXlluKEVd3NSUjjuyEbLmaK3HlBjj129fo+FpvCCRcwEVjavjw5vRanSrDibCEgq9acgDroyywagqiUDoDGXpDuEkdGrHL5r8HXq7PBNpQPrGiv/4QE+n/jjKs28rU5eVJnUcbmrx7lzkpJhmPpLWWt6z3SPc7A4H+5V/GNM8u3FzmtWmOZCYdOYpvJSqj5bOb+C+J6DkrKMUj92EygZ7bdYVvMduuu2GpAQKBgQDi6SBI7KQcg5iQfEYiEtI+gYqTlqxvcckOVjvR+BETDT4BTT9l5hY5kgI4tPIaIkxYmgMV/9KEKfpzl7QB/plE7QfO74iqFSKIjA8255cMYQfEvsl4aycuWjX1k8qurdYVd6Zf0YJx8hImzIrC3F+RlNLG1duJrlTc1+dTsyfBqQKBgQD4B/P11sq8Ff0gbkK0yha2wx1MAB1tJbQrmsBLEUxEOvEyZWc5HP5JcpiuWZ2ez9BmRBuqoHJdbLeVed28LFuHGNnnenjPtFYueCUtn08WbPRiAsSvl9KBYUSNJGcTzx2DnPKff5g4qpxtpy1xatjlO90Qa19tBWBRH74h3HyLUQKBgHYqc16mpXwX585ueVNrO8PDUDg1wgyzU8G8lKBur+2c+spQwRA+txo5+CAx0tZbQCjv42WEeMvM1Jl3yg0z2ftz+JoCUchJA1faoF22UwnmSdZiiKbGHnxcDDecRHOdIZzCV4kUoPrX1i4QXL7HZpl4yB815YMYnyb25IfJB44pAoGBAN5+G90KucAxJWkDH7mXkvqNWmmDrOHtMYrv/dEZCkYmbZkcl5UHKiBuuQGliBwfJT2nmP3dgdnGnYRr1nlnfQ9aPpZEKb2ActKqmgcr4kEmqa6F9zPgyEehHmJSJlkBsOMQViC3z6/9c+d1UPFtvHUd1zdUajyc1wLOq7sqU1qRAoGAFnTsn0jUC5aBAteFlYjY9jWpD9JfyjO7619G/ttAGqeuw9uwAkudErkRb/TPJ3YKfWK/4sr4UZYw18ODdBU1zrUtoCNVSMCC0D+lBufazv7liqdm/ZHGMt8KLweoE9d8Jx7GhWnwNSAwEwLMmqq94txpccpQ4KocGJ8KYNEXh+M=";

    }

    /**
     * 随机生成RSA密钥对
     *
     * @param keySize 密钥长度，范围：512-2048
     */
    public static KeyPair generateKeyPair(int keySize) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
            kpg.initialize(keySize);
            return kpg.genKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用公钥对字符串进行加密
     *
     * @param data 原文
     * @param key 公钥
     */
    public static byte[] encryptWithPublicKey(byte[] data, byte[] key) throws Exception {
        Cipher cp = Cipher.getInstance(ECB_PADDING);
        cp.init(Cipher.ENCRYPT_MODE, getPublicKey(key));
        return cp.doFinal(data);
    }

    /**
     * 用公钥对字符串进行加密
     *
     * @param plain 原文
     * @param publicKey 公钥
     */
    public static String encryptWithPublicKey(String plain, String publicKey) throws Exception {
        byte[] key = Base64.decodeBase64(publicKey);
        byte[] data = plain.getBytes("UTF-8");
        return Base64.encodeBase64String(encryptWithPublicKey(data, key));
    }

    /**
     * 公钥解密
     *
     * @param data 待解密数据
     * @param key  密钥
     */
    public static byte[] decryptWithPublicKey(byte[] data, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(ECB_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, getPublicKey(key));
        return cipher.doFinal(data);
    }

    /**
     * 私钥加密
     *
     * @param data 待加密数据
     * @param key  密钥
     */
    public static byte[] encryptWithPrivateKey(byte[] data, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(ECB_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(key));
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     *
     * @param data 待解密数据
     * @param key  密钥
     */
    public static byte[] decryptWithPrivateKey(byte[] data, byte[] key) throws Exception {
        Cipher cp = Cipher.getInstance(ECB_PADDING);
        cp.init(Cipher.DECRYPT_MODE, getPrivateKey(key));
        byte[] arr = cp.doFinal(data);
        return arr;
    }

    public static PublicKey getPublicKey(byte[] key) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey getPrivateKey(byte[] key) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 分块加密
     *
     * @param plain 原文
     * @param publicKey 公钥
     */
    public static String encryptWithPublicKeyBlock(String plain, String publicKey) throws Exception {
        byte[] data = plain.getBytes("UTF-8");
        byte[] key = Base64.decodeBase64(publicKey);

        byte[] result = encryptWithPublicKeyBlock(data, key);
        return Base64.encodeBase64String(result);
    }

    /**
     * 分块解密
     *
     * @param plain 密文
     * @param privateKey 私钥
     */
    public static String decryptWithPrivateKeyBlock(String plain, String privateKey) {
        try {
            byte[] data = Base64.decodeBase64(plain.getBytes("UTF-8"));
            byte[] key = Base64.decodeBase64(privateKey);

            byte[] bytes = decryptWithPrivateKeyBlock(data, key);
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取公钥，私钥对
     * @return get(0)公钥，get(1)私钥
     */
    public static Map<Integer, String> genKeyPair() {

        Map<Integer, String> keyMap = new HashMap<Integer, String>(); // 用于封装随机产生的公钥与私钥

        try {
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = generateKeyPair(KEY_SIZE);
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 得到私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 得到公钥

            // 得到公钥字符串
            String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
            // 得到私钥字符串
            String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));

            // 将公钥和私钥保存到Map
            keyMap.put(0, publicKeyString); // 0表示公钥
            keyMap.put(1, privateKeyString); // 1表示私钥
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return keyMap;
    }

    /**
     * 分块加密
     *
     * @param data
     * @param key
     */
    public static byte[] encryptWithPublicKeyBlock(byte[] data, byte[] key) throws Exception {
        int blockCount = (data.length / ENCRYPT_BLOCK);

        if ((data.length % ENCRYPT_BLOCK) != 0) {
            blockCount += 1;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream(blockCount * ENCRYPT_BLOCK);
        Cipher cipher = Cipher.getInstance(ECB_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(key));

        for (int offset = 0; offset < data.length; offset += ENCRYPT_BLOCK) {
            int inputLen = (data.length - offset);
            if (inputLen > ENCRYPT_BLOCK) {
                inputLen = ENCRYPT_BLOCK;
            }
            byte[] encryptedBlock = cipher.doFinal(data, offset, inputLen);
            bos.write(encryptedBlock);
        }

        bos.close();
        return bos.toByteArray();
    }

    /**
     * 分块加密
     *
     * @param data
     * @param key
     */
    public static byte[] encryptWithPrivateKeyBlock(byte[] data, byte[] key) throws Exception {
        int blockCount = (data.length / ENCRYPT_BLOCK);

        if ((data.length % ENCRYPT_BLOCK) != 0) {
            blockCount += 1;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream(blockCount * ENCRYPT_BLOCK);
        Cipher cipher = Cipher.getInstance(ECB_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(key));

        for (int offset = 0; offset < data.length; offset += ENCRYPT_BLOCK) {
            int inputLen = (data.length - offset);
            if (inputLen > ENCRYPT_BLOCK) {
                inputLen = ENCRYPT_BLOCK;
            }
            byte[] encryptedBlock = cipher.doFinal(data, offset, inputLen);
            bos.write(encryptedBlock);
        }

        bos.close();
        return bos.toByteArray();
    }

    /**
     * 分块解密
     *
     * @param data
     * @param key
     */
    public static byte[] decryptWithPublicKeyBlock(byte[] data, byte[] key) throws Exception {
        int blockCount = (data.length / DECRYPT_BLOCK);
        if ((data.length % DECRYPT_BLOCK) != 0) {
            blockCount += 1;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream(blockCount * DECRYPT_BLOCK);
        Cipher cipher = Cipher.getInstance(ECB_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, getPublicKey(key));
        for (int offset = 0; offset < data.length; offset += DECRYPT_BLOCK) {
            int inputLen = (data.length - offset);
            if (inputLen > DECRYPT_BLOCK) {
                inputLen = DECRYPT_BLOCK;
            }
            byte[] decryptedBlock = cipher.doFinal(data, offset, inputLen);
            bos.write(decryptedBlock);
        }

        bos.close();
        return bos.toByteArray();
    }

    /**
     * 分块解密
     *
     * @param data
     * @param key
     */
    public static byte[] decryptWithPrivateKeyBlock(byte[] data, byte[] key) throws Exception {
        int blockCount = (data.length / DECRYPT_BLOCK);
        if ((data.length % DECRYPT_BLOCK) != 0) {
            blockCount += 1;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream(blockCount * DECRYPT_BLOCK);
        Cipher cipher = Cipher.getInstance(ECB_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(key));
        for (int offset = 0; offset < data.length; offset += DECRYPT_BLOCK) {
            int inputLen = (data.length - offset);

            if (inputLen > DECRYPT_BLOCK) {
                inputLen = DECRYPT_BLOCK;
            }

            byte[] decryptedBlock = cipher.doFinal(data, offset, inputLen);
            bos.write(decryptedBlock);
        }

        bos.close();
        return bos.toByteArray();
    }
}