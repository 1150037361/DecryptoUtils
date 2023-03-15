package utils;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SM4;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.nio.charset.StandardCharsets;

public class SM4Util {
    public static String Encrypt(String src, String key, String cyrptoType, String codeType, String iv) {
        String[] mode = cyrptoType.split("/");
        if (mode[0].equals("ECB")) {
            SM4 aes = new SM4(Mode.ECB, Padding.valueOf(mode[1]), generateKey(key));
            if (codeType.equals("HEX")) {
                return aes.encryptHex(src);
            } else {
                return aes.encryptBase64(src);
            }
        } else {
            SM4 aes = new SM4(Mode.CBC, Padding.valueOf(mode[1]), generateKey(key), generateKey(iv));
            if (codeType.equals("HEX")) {
                return aes.encryptHex(src);
            } else {
                return aes.encryptBase64(src);
            }
        }
    }

    public static String Decrypt(String src, String key, String cyrptoType, String iv) {
        String[] mode = cyrptoType.split("/");
        if (mode[0].equals("ECB")) {
            SM4 aes = new SM4(Mode.ECB, Padding.valueOf(mode[1]), generateKey(key));
            return aes.decryptStr(src);
        } else {
            SM4 aes = new SM4(Mode.CBC, Padding.valueOf(mode[1]), generateKey(key), generateKey(iv));
            return aes.decryptStr(src);
        }
    }

    public static byte[] generateKey(String key){
        byte[] keys = key.getBytes(StandardCharsets.UTF_8);
        byte[] raw = new byte[16];
        byte[] plusbyte={ 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
        for(int i=0;i<16;i++)
        {
            if (keys.length > i)
                raw[i] = keys[i];
            else
                raw[i] = plusbyte[0];
        }
        return raw;
    }
}
