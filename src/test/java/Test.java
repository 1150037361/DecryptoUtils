import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.io.InputStream;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws Exception {
        Context rhinoContext = Context.enter();
        try {
            // 初始化全局作用域对象
            Scriptable scope = rhinoContext.initStandardObjects();
            rhinoContext.evaluateString(scope,getCryptoJs(), "crypto-js.js",1,null);

            // 使用 CryptoJS 进行 MD5 加密
            String code = "function wxEncryptData (word) {\n" +
                    "    var key = CryptoJS.SHA1(CryptoJS.SHA1(\"KEY@20220306.\")).toString().substring(0, 32);\n" +
                    "    var srcs = CryptoJS.enc.Utf8.parse(word);\n" +
                    "    var encrypted = CryptoJS.AES.encrypt(srcs, CryptoJS.enc.Hex.parse(key), {\n" +
                    "        mode: CryptoJS.mode.ECB,\n" +
                    "        padding: CryptoJS.pad.Pkcs7\n" +
                    "    })\n" +
                    "    return encrypted.ciphertext.toString().toUpperCase();\n" +
                    "};\n" +
                    "\n" +
                    "function wxDecryptData (data) {\n" +
                    "    const wordArray = CryptoJS.enc.Hex.parse(data)\n" +
                    "    var base64Word = CryptoJS.enc.Base64.stringify(wordArray)\n" +
                    "    var key = CryptoJS.SHA1(CryptoJS.SHA1(\"KEY@20220306.\")).toString().substring(0, 32);\n" +
                    "    var encrypted = CryptoJS.AES.decrypt(base64Word, CryptoJS.enc.Hex.parse(key), {\n" +
                    "        mode: CryptoJS.mode.ECB,\n" +
                    "        padding: CryptoJS.pad.Pkcs7\n" +
                    "    })\n" +
                    "    return CryptoJS.enc.Utf8.stringify(encrypted).toString()\n" +
                    "};";
            rhinoContext.evaluateString(scope,code, "code.js",1,null);
            String name = "wxDecryptData";
            String data = "2D2C9A7EC246F50543C95ADF676E3430";
            Object result = rhinoContext.evaluateString(scope, name + "('" + data + "')", "<cmd>", 1, null);
            System.out.println(result);
//            String input = "Hello, World!";
//            String md5Hash = (String) engine.eval("CryptoJS.MD5('" + input + "').toString()");
//
//            System.out.println("MD5 hash: " + md5Hash);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCryptoJs() {
        String result = null;
        ClassLoader classLoader = Test.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("crypto-js/crypto-js.js");
        if (inputStream != null) {
            try (Scanner scanner = new Scanner(inputStream, "UTF-8")) {
                StringBuilder stringBuilder = new StringBuilder();

                // 逐行读取文件内容并拼接到 StringBuilder
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    stringBuilder.append(line).append("\n");
                }

                result = stringBuilder.toString();
            }
        } else {
            System.out.println("文件不存在或无法读取。");
        }
        return result;
    }
}
