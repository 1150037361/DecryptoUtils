import com.alibaba.fastjson.JSON;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;

public class Test {
    public static void main(String[] args) throws Exception {
        Frame a = new Frame();
        JTextArea jTextArea = new RSyntaxTextArea(5,10);
        RSyntaxTextArea area = (RSyntaxTextArea) jTextArea;
        area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        LanguageSupportFactory.get().register(area);
        area.setMarkOccurrences(true);
        area.setCodeFoldingEnabled(true);
        area.setTabsEmulated(true);
        a.add(jTextArea);
        a.setVisible(true);
    }
    public static boolean isJson(String content) {
        try {
            Object obj = JSON.parse(content);
            return true;
        } catch (Exception e) {
            return false;
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

    public static byte[] replaceBytes(byte[] source, int start, int end, byte[] replacement) {
        byte[] result = new byte[start + replacement.length + (source.length - end - 1)];
        System.arraycopy(source, 0, result, 0, start); // 复制 source 中的前半部分
        System.arraycopy(replacement, 0, result, start, replacement.length); // 复制 replacement
        System.arraycopy(source, end+1, result, start+replacement.length, source.length-end-1); // 复制 source 中的后半部分
        return result;
    }
}
