package utils;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesUtil {
    /**
     * 加密
     *
     * @param src src 加密字符串
     * @param key key 密钥
     * @return 加密后的字符串
     */
    public static String Encrypt(String src, String key, String cyrptoType, String codeType, String iv) {
        String[] mode = cyrptoType.split("/");
        if (mode[0].equals("ECB")) {
            AES aes = new AES(Mode.ECB, Padding.valueOf(mode[1]), generateKey(key));
            if (codeType.equals("HEX")) {
                return aes.encryptHex(src);
            } else {
                return aes.encryptBase64(src);
            }
        } else {
            AES aes = new AES(Mode.CBC, Padding.valueOf(mode[1]), generateKey(key), generateKey(iv));
            if (codeType.equals("HEX")) {
                return aes.encryptHex(src);
            } else {
                return aes.encryptBase64(src);
            }
        }
    }

    /**
     * 解密
     *
     * @param src src 解密字符串
     * @param key key 密钥
     * @return 解密后的字符串
     */
    public static String Decrypt(String src, String key, String cyrptoType, String iv) {
        String[] mode = cyrptoType.split("/");
        if (mode[0].equals("ECB")) {
            AES aes = new AES(Mode.ECB, Padding.valueOf(mode[1]), generateKey(key));
            return aes.decryptStr(src);
        } else {
            AES aes = new AES(Mode.CBC, Padding.valueOf(mode[1]), generateKey(key), generateKey(iv));
            return aes.decryptStr(src);
        }
    }

    /**
     * 将byte[]转为各种进制的字符串
     * @param bytes byte[]
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
//    public static String binary(byte[] bytes, int radix){
//        return BigInteger(1, bytes).toString(radix);   // 这里的1代表正数
//    }

    /**
     * 16进制的字符串表示转成字节数组
     *
     * @param hexString 16进制格式的字符串
     * @return 转换后的字节数组
     **/
    public static byte[] toByteArray(String hexString) {
        if (hexString.isEmpty())
            throw new IllegalArgumentException("this hexString must not be empty");

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public static byte[] generateKey(String key) {
        byte[] keys = key.getBytes(StandardCharsets.UTF_8);
        byte[] raw = new byte[16];
        byte[] plusbyte = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
        for (int i = 0; i < 16; i++) {
            if (keys.length > i)
                raw[i] = keys[i];
            else
                raw[i] = plusbyte[0];
        }
        return raw;
    }
}
