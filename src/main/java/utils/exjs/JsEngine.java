package utils.exjs;

import burp.BurpExtender;
import org.apache.commons.text.StringEscapeUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.io.InputStream;
import java.util.Scanner;

public class JsEngine {
    public Context rhinoContext;
    public Scriptable scope;
    public String enMethodName;
    public String deMethodName;
    public String jsCode;
    public BurpExtender burpExtender;

    public JsEngine(BurpExtender burpExtender,String jsCode,String enMethodName, String deMethodName) {
        this.burpExtender = burpExtender;
        rhinoContext = Context.enter();
        this.jsCode = jsCode;
        this.enMethodName = enMethodName;
        this.deMethodName = deMethodName;

        try {
            scope = rhinoContext.initStandardObjects();
            rhinoContext.evaluateString(scope,getCryptoJs(), "crypto-js.js",1,null);
            rhinoContext.evaluateString(scope,jsCode, "code.js",1,null);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Init(String jsCode,String enMethodName, String deMethodName) {
        Context.exit();
        rhinoContext = Context.enter();
        this.jsCode = jsCode;
        this.enMethodName = enMethodName;
        this.deMethodName = deMethodName;

        try {
            scope = rhinoContext.initStandardObjects();
            rhinoContext.evaluateString(scope,getCryptoJs(), "crypto-js.js",1,null);
            rhinoContext.evaluateString(scope,jsCode, "code.js",1,null);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encode(String src) {
        String result = null;
        Function encodeFunction = (Function) scope.get(enMethodName,scope);
        result = (String) encodeFunction.call(rhinoContext,scope,scope,new Object[]{src});
        return result;
    }

    public String decode(String src) {
        String result = null;
        Function encodeFunction = (Function) scope.get(deMethodName,scope);
        result = (String) encodeFunction.call(rhinoContext,scope,scope,new Object[]{src});
        return result;
    }

    public String getCryptoJs() {
        String result = null;
        ClassLoader classLoader = JsEngine.class.getClassLoader();
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
