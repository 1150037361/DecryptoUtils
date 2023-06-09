package utils;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

public class RsaUtil {
    public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCY/433DgqLGMvDgGTk9NXgPZxPp9e6i60MDNG6NwAR2YKGNuUNluvEiDBK7/oUVeYCeTe8x7csmilAO8butre8f7uGEsr0x6/0/TYdJVv/IClk521ZoLmmDmWygN5J8iZd/9Y7S4RmB3o9tInx5kN4CZJjtua2YnqYWlCN3iFyPQIDAQAB";
    public static final String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJj/jfcOCosYy8OAZOT01eA9nE+n17qLrQwM0bo3ABHZgoY25Q2W68SIMErv+hRV5gJ5N7zHtyyaKUA7xu62t7x/u4YSyvTHr/T9Nh0lW/8gKWTnbVmguaYOZbKA3knyJl3/1jtLhGYHej20ifHmQ3gJkmO25rZiephaUI3eIXI9AgMBAAECgYAJV8GxjnfyWodG3vZKTPEqilibPKKdz523mKjN+EHW1TN1QrDmDYtEKxTX3qOkzkIHcKOIsaFr5dYTCBNzzFCX5/PlFl8N7DPmpkLys7UvCq/V+EsRdZdKH/wcYbAoRYyB5XRLrv+IUNDchXr6fgTG5WLLmL7nvZwDbu1NW7jJeQJBANNRNz2oySjbF0l11qk5sm3F3B4m2GeDQ47OpbnoHelRd2k3mlnpbm8hnwYhMqL88RW6Yuy1c7wxAkc9WcgLlx8CQQC5WXtWVv06KDnOQnPcOKP1HYLkllpJ7JV2npMfdXiPHVkX82CjGk6yf21Gr16beF0bZU8+dESJIj5AD7sfCBcjAkAa/c2zh4KiQFHaJT4VAXSkBtjV1RphJmgTrpuGgnqHmctJ2jtR7roXYdEBJG5mWS6mGteV6pts10m2NcTpA2n7AkBRJgkm2v0ROk1craF2OGIoTdxh4iXzmZ4yiejV7CN1XR8FqmuT3U+5PKsGOH+OR9tTxD42lMbw10oz1+9P/cXLAkASATIgh5sF5qGWa3XYM3iOXq3tPv2lOJnnkwuPJy8+MZfZS5Y0U6d6SCF5SCghyuj3bAXfuVdzC86h/X9VhtGV";

    public static String enCrypto(String src, String publicKey, String codeType) {
        RSA rsa = new RSA(null, publicKey);
        if (codeType.equals("HEX")) {
            return rsa.encryptHex(src, KeyType.PublicKey);
        } else {
            return rsa.encryptBase64(src, KeyType.PublicKey);
        }
    }

    public static String deCrypto(String src, String privateKey) {
        RSA rsa = new RSA(privateKey, null);
        return rsa.decryptStr(src, KeyType.PrivateKey);
    }

    public static void main(String[] args) {
//        String data = "1145141919810";
//        System.out.println("加密数据： " + enCrypto(data, publicKey));
//        System.out.println("解密数据： " + deCrypto(enCrypto(data, publicKey), privateKey));
    }
}
