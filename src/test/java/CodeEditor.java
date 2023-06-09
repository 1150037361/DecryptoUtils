import cn.hutool.core.net.URLDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CodeEditor {
    public static void main(String[] args) {
        String data = "%%%%%%%%%%%%";
        System.out.println(URLDecoder.decode(data, StandardCharsets.UTF_8));
    }
}