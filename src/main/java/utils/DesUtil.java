package utils;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.DESede;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DesUtil {
    public static String enCrypto(String src, String key, String cyrptoType, String codeType, String iv) {
        String[] mode = cyrptoType.split("/");
        if (mode[0].equals("ECB")) {
            DES des = new DES(Mode.ECB, Padding.valueOf(mode[1]), generateKey(key));
            if (codeType.equals("HEX")) {
                return des.encryptHex(src);
            } else {
                return des.encryptBase64(src);
            }
        } else {
            DES des = new DES(Mode.CBC, Padding.valueOf(mode[1]), generateKey(key), generateIv(iv));
            if (codeType.equals("HEX")) {
                return des.encryptHex(src);
            } else {
                return des.encryptBase64(src);
            }
        }
    }

    public static String enCrypto(String src, byte[] key, String cyrptoType, String codeType, byte[] iv) {
        String[] mode = cyrptoType.split("/");
        if (mode[0].equals("ECB")) {
            DES des = new DES(Mode.ECB, Padding.valueOf(mode[1]), key);
            if (codeType.equals("HEX")) {
                return des.encryptHex(src);
            } else {
                return des.encryptBase64(src);
            }
        } else {
            DES des = new DES(Mode.CBC, Padding.valueOf(mode[1]), key, iv);
            if (codeType.equals("HEX")) {
                return des.encryptHex(src);
            } else {
                return des.encryptBase64(src);
            }
        }
    }

    public static String enTripleDES(String src, String key, String cyrptoType, String codeType, String iv) {
        String[] mode = cyrptoType.split("/");
        if (mode[0].equals("ECB")) {
            DESede des3 = new DESede(Mode.ECB, Padding.valueOf(mode[1]), generate3DesKey(key));
            if (codeType.equals("HEX")) {
                return des3.encryptHex(src);
            } else {
                return des3.encryptBase64(src);
            }
        } else {
            DESede des3 = new DESede(Mode.CBC, Padding.valueOf(mode[1]), generate3DesKey(key), generateIv(iv));
            if (codeType.equals("HEX")) {
                return des3.encryptHex(src);
            } else {
                return des3.encryptBase64(src);
            }
        }
    }

    public static String enTripleDES(String src, byte[] key, String cyrptoType, String codeType, byte[] iv) {
        String[] mode = cyrptoType.split("/");
        if (mode[0].equals("ECB")) {
            DESede des3 = new DESede(Mode.ECB, Padding.valueOf(mode[1]), key);
            if (codeType.equals("HEX")) {
                return des3.encryptHex(src);
            } else {
                return des3.encryptBase64(src);
            }
        } else {
            DESede des3 = new DESede(Mode.CBC, Padding.valueOf(mode[1]), key, iv);
            if (codeType.equals("HEX")) {
                return des3.encryptHex(src);
            } else {
                return des3.encryptBase64(src);
            }
        }
    }

    public static String deTripleCrypto(String src, String key, String cyrptoType, String iv) {
        String[] mode = cyrptoType.split("/");
        if (mode[0].equals("ECB")) {
            DESede des3 = new DESede(Mode.ECB, Padding.valueOf(mode[1]), generate3DesKey(key));
            return des3.decryptStr(src);
        } else {
            DESede des3 = new DESede(Mode.CBC, Padding.valueOf(mode[1]), generate3DesKey(key), generateIv(iv));
            return des3.decryptStr(src);
        }
    }

    public static String deTripleCrypto(String src, byte[] key, String cyrptoType, byte[] iv) {
        String[] mode = cyrptoType.split("/");
        if (mode[0].equals("ECB")) {
            DESede des3 = new DESede(Mode.ECB, Padding.valueOf(mode[1]), key);
            return des3.decryptStr(src);
        } else {
            DESede des3 = new DESede(Mode.CBC, Padding.valueOf(mode[1]), key, iv);
            return des3.decryptStr(src);
        }
    }

    public static String deCrypto(String src, String key, String cyrptoType, String iv) {
        String[] mode = cyrptoType.split("/");
        if (mode[0].equals("ECB")) {
            DES des = new DES(Mode.ECB, Padding.valueOf(mode[1]), generateKey(key));
            return des.decryptStr(src);
        } else {
            DES des = new DES(Mode.CBC, Padding.valueOf(mode[1]), generateKey(key), generateIv(iv));
            return des.decryptStr(src);
        }
    }

    public static String deCrypto(String src, byte[] key, String cyrptoType, byte[] iv) {
        String[] mode = cyrptoType.split("/");
        if (mode[0].equals("ECB")) {
            DES des = new DES(Mode.ECB, Padding.valueOf(mode[1]), key);
            return des.decryptStr(src);
        } else {
            DES des = new DES(Mode.CBC, Padding.valueOf(mode[1]), key, iv);
            return des.decryptStr(src);
        }
    }




    public static byte[] generateKey(String key) {
        byte[] keys = key.getBytes(StandardCharsets.UTF_8);
        byte[] raw = new byte[8];
        byte[] plusbyte = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
        for (int i = 0; i < 8; i++) {
            if (keys.length > i)
                raw[i] = keys[i];
            else
                raw[i] = plusbyte[0];
        }
        return raw;
    }

    public static byte[] generate3DesKey(String key) {
        byte[] keys = key.getBytes(StandardCharsets.UTF_8);
        byte[] raw = new byte[24];
        byte[] plusbyte = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
        for (int i = 0; i < 24; i++) {
            if (keys.length > i)
                raw[i] = keys[i];
            else
                raw[i] = plusbyte[0];
        }
        return raw;
    }

    public static byte[] generateIv(String iv) {
        byte[] keys = iv.getBytes(StandardCharsets.UTF_8);
        byte[] raw = new byte[8];
        byte[] plusbyte = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
        for (int i = 0; i < 8; i++) {
            if (keys.length > i)
                raw[i] = keys[i];
            else
                raw[i] = plusbyte[0];
        }
        return raw;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] key = Base64.getDecoder().decode("uQLzIOVO0l+6KZMyn6qPFJcTFOTYXej2L72kkM06FgM=");
//        String key = "123456789123456";
        String iv = "01234567";
        String mode = "ECB/ZeroPadding";
        String data = "SeFUNPOwm3AC26lPa5+Sv/y1sJbkXQpY+jOVkY1H+tjgYC6Xng1cf1yv0yzjYqiVifDLzTw6ejCz+ScZwUnpDlsUAquwAFNMvJJ2IKvK34X+IZpZ+UUXDraeY9c7l5ghXthSx6bEUfH31NlI+Vl7RB43dlHGBVmxp1KD9GreJa2MpfmkVJHJ3XK6wbeRc5b8lY9H/80atBPT8S6IO4ORVA==";
        System.out.println(deCrypto(data,key,mode,iv.getBytes(StandardCharsets.UTF_8)));
    }
}
