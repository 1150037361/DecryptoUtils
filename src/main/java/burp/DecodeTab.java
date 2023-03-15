package burp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import utils.AesUtil;
import utils.SM2Util;
import utils.SM4Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DecodeTab implements IMessageEditorTab {
    private BurpExtender burpExtender;
    private HashMap<String, String> conf;
    private IExtensionHelpers helpers;
    private JButton deCryptoButton;
    private JButton enCryptoButton;
    private JPanel tabPanel;
    private boolean editable;
    private ITextEditor txtInput;
    private byte[] currentMessage;
    private int parameterStart;
    private int parameterEnd;
    private String decodeData = null;

    public DecodeTab() {

    }

    public DecodeTab(IMessageEditorController controller, boolean editable, BurpExtender burpExtender) {
        this.editable = editable;
        this.burpExtender = burpExtender;
        conf = burpExtender.getConf();
        helpers = burpExtender.getHelpers();
        txtInput = burpExtender.getCallbacks().createTextEditor();
        txtInput.setEditable(editable);
        tabPanel = creatTabPanel();
    }

    //创建自己Tab
    public JPanel creatTabPanel() {
        JPanel cryptPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        deCryptoButton = new JButton("解密");
        enCryptoButton = new JButton("加密");
        JButton testButton = new JButton("测试");


        cryptPanel.setLayout(new BorderLayout(0, 0));
        cryptPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        // buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        buttonPanel.add(testButton);
        buttonPanel.add(enCryptoButton);
        buttonPanel.add(deCryptoButton);


        cryptPanel.add(buttonPanel, BorderLayout.NORTH);
        cryptPanel.add(txtInput.getComponent(), BorderLayout.CENTER);
        testButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, new String(getSelectedData()) + "\n" + helpers.bytesToString(getMessage()), "信息", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        enCryptoButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (conf.get("isEnable").equals("open")) {
                    EncodeData();
                } else {
                    JOptionPane.showMessageDialog(null, "请检查是否开启并保存配置", "错误！", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deCryptoButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (conf.get("isEnable").equals("open")) {
                    DecodeData();
                } else {
                    JOptionPane.showMessageDialog(null, "请检查是否开启并保存配置", "错误！", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return cryptPanel;
    }

    //加密数据方法
    public void EncodeData() {
        String source;
        String cryptoType;
        boolean isRequest = true;
        if (isRequest()) {
            source = getParameterValue();
            cryptoType = conf.get("reqCryptoType");
        } else {
            source = getBodyValue();
            cryptoType = conf.get("resCryptoType");
            isRequest = false;
        }
        if (source.contains("\\\"")) {
            source = source.replace("\\\"", "\"").replace("\\n","").replace("\\t","");
            burpExtender.printLog(source);
        }
        switch (cryptoType) {
            case "AES":
                try {
                    aesCrypto(true, URLDecoder.decode(source, "UTF-8").replace(" ", "+"), isRequest);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "请检查密钥或填充是否正确", "错误！", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "SM4":
                try {
                    sm4Crypto(true, URLDecoder.decode(source, "UTF-8").replace(" ", "+"), isRequest);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "请检查密钥或填充是否正确", "错误！", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "SM2":
                try {
                    sm2Crypto(true, URLDecoder.decode(source, "UTF-8").replace(" ", "+"), isRequest);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "请检查公私钥是否正确！！", "错误！", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }

    }

    //解密数据方法
    public void DecodeData() {
        String source;
        String cryptoType;
        boolean isRequest = true;
        if (isRequest()) {
            source = getParameterValue();
            cryptoType = conf.get("reqCryptoType");
        } else {
            isRequest = false;
            source = getBodyValue();
            cryptoType = conf.get("resCryptoType");
        }
        switch (cryptoType) {
            case "AES":
                try {
                    aesCrypto(false, URLDecoder.decode(source, "UTF-8").replace(" ", "+"), isRequest);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "请检查密钥或填充是否正确", "错误！", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "SM4":
                try {
                    sm4Crypto(false, URLDecoder.decode(source, "UTF-8").replace(" ", "+"), isRequest);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "请检查密钥或填充是否正确", "错误！", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "SM2":
                try {
                    sm2Crypto(false, URLDecoder.decode(source, "UTF-8").replace(" ", "+"), isRequest);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "请检查公私钥是否正确！！", "错误！", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
    }

    //判断是否为request请求
    public boolean isRequest() {
        String data = helpers.bytesToString(txtInput.getText());
        if (data.startsWith("HTTP")) {
            return false;
        } else {
            return true;
        }
    }

    //使用自己的方法获取字段的值，官方api会导致乱码,官方不是默认UTF-8
    public String getTrueParameterValue(IParameter parameter) {
        parameterStart = parameter.getValueStart();
        parameterEnd = parameter.getValueEnd();
        return new String(Arrays.copyOfRange(txtInput.getText(), parameterStart, parameterEnd));
    }

    //获取字段内容
    public String getParameterValue() {
        if (conf.get("reqParameter").equals("AllData")) {
            return getBodyValue();
        } else {
            IParameter parameter = helpers.getRequestParameter(txtInput.getText(), conf.get("reqParameter"));
            return getTrueParameterValue(parameter);
        }
    }

    //获取body的内容
    public String getBodyValue() {
        int bodyOffset = helpers.analyzeRequest(txtInput.getText()).getBodyOffset();
        String body = new String(txtInput.getText()).substring(bodyOffset);
        return body;
    }

    public String getBodyValue(byte[] conten) {
        int bodyOffset = helpers.analyzeRequest(conten).getBodyOffset();
        String body = new String(conten).substring(bodyOffset);
        return body;
    }

    @Override
    public String getTabCaption() {
        return "Decode++";
    }

    @Override
    public Component getUiComponent() {
        return tabPanel;
    }

    @Override
    public boolean isEnabled(byte[] content, boolean isRequest) {
        return true;
    }

    @Override
    public void setMessage(byte[] content, boolean isRequest) {
        currentMessage = content;
        txtInput.setText(content);
    }

    public void setPrettyMessage(byte[] content) {
        String body = getBodyValue(content);
        if (isJson(body)) {
            body = getPrettyJson(body);
        }
        byte[] newMessage = helpers.buildHttpMessage(helpers.analyzeRequest(content).getHeaders(), body.getBytes(StandardCharsets.UTF_8));
        txtInput.setText(newMessage);
    }

    @Override
    public byte[] getMessage() {
        return txtInput.getText();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public byte[] getSelectedData() {
        return txtInput.getSelectedText();
    }

    //aes加解密方法
    public void aesCrypto(boolean isEncode, String data, boolean isRequest) {

        if (isEncode) {
            if (isRequest) {
                String result = AesUtil.Encrypt(data, conf.get("reqCryptoKey"), conf.get("reqCryptoPadding"), conf.get("reqCryptoCode"), conf.get("reqCryptoIv"));
                updateContent(result);
            } else {
                String result = AesUtil.Encrypt(data, conf.get("resCryptoKey"), conf.get("resCryptoPadding"), conf.get("resCryptoCode"), conf.get("resCryptoIv"));
                updateContent(result);
            }

        } else {
            if (isRequest) {
                String result = AesUtil.Decrypt(data, conf.get("reqCryptoKey"), conf.get("reqCryptoPadding"), conf.get("reqCryptoIv"));
                updateContent(result);
            } else {
                String result = AesUtil.Decrypt(data, conf.get("resCryptoKey"), conf.get("resCryptoPadding"), conf.get("resCryptoIv"));
                updateContent(result);
            }
        }
    }

    //sm4加解密方法
    public void sm4Crypto(boolean isEncode, String data, boolean isRequest) {
        if (isEncode) {
            if (isRequest) {
                String result = SM4Util.Encrypt(data, conf.get("reqCryptoKey"), conf.get("reqCryptoPadding"), conf.get("reqCryptoCode"), conf.get("reqCryptoIv"));
                updateContent(result);
            } else {
                String result = SM4Util.Encrypt(data, conf.get("resCryptoKey"), conf.get("resCryptoPadding"), conf.get("resCryptoCode"), conf.get("resCryptoIv"));
                updateContent(result);
            }

        } else {
            if (isRequest) {
                String result = SM4Util.Decrypt(data, conf.get("reqCryptoKey"), conf.get("reqCryptoPadding"), conf.get("reqCryptoIv"));
                updateContent(result);
            } else {
                String result = SM4Util.Decrypt(data, conf.get("resCryptoKey"), conf.get("resCryptoPadding"), conf.get("resCryptoIv"));
                updateContent(result);
            }
        }
    }

    //sm2加解密方法
    public void sm2Crypto(boolean isEncode, String data, boolean isRequest) {
        if (isEncode) {
            if (isRequest) {
                String result = SM2Util.Encrypt(data, conf.get("reqCryptoPublicKey"));
                updateContent(result);
            } else {
                String result = SM2Util.Encrypt(data, conf.get("resCryptoPublicKey"));
                updateContent(result);
            }
        } else {
            if (isRequest) {
                String result = SM2Util.Decrypt(data, conf.get("reqCryptoKey"));
                updateContent(result);
            } else {
                String result = SM2Util.Decrypt(data, conf.get("resCryptoKey"));
                updateContent(result);
            }
        }
    }

    public boolean isJson(String content) {
        try {
            if (!content.startsWith("{")) {
                return false;
            }
            Object obj = JSON.parse(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getPrettyJson(String data) {
        return JSON.toJSONString(new JSONObject(JSON.parseObject(data)), true);
    }

    //使用自己的方法更新字段值，官方api会导致乱码
    public void updateParameter(byte[] replace) {
        parameterEnd -= 1;
        burpExtender.printLog("start = " + parameterStart);
        burpExtender.printLog("end = " + parameterEnd);
        byte[] source = txtInput.getText();
        byte[] result = new byte[parameterStart + replace.length + (source.length - parameterEnd - 1)];
        System.arraycopy(source, 0, result, 0, parameterStart); // 复制 source 中的前半部分
        System.arraycopy(replace, 0, result, parameterStart, replace.length); // 复制 replacement
        System.arraycopy(source, parameterEnd + 1, result, parameterStart + replace.length, source.length - parameterEnd - 1);
        setPrettyMessage(result);
    }

    //更新数据内容
    public void updateContent(String result) {
        //判断是否为request请求
        if (isRequest()) {
            //判断是否为URL内的字段
            if (conf.get("reqDataType").equals("URL")) {
                burpExtender.printLog(result);
                updateParameter(result.getBytes(StandardCharsets.UTF_8));
            } else {
                if (conf.get("reqParameter").equals("AllData")) {
                    byte[] newMessage = helpers.buildHttpMessage(helpers.analyzeRequest(txtInput.getText()).getHeaders(), result.getBytes(StandardCharsets.UTF_8));
                    setPrettyMessage(newMessage);
                } else {
                    if (!result.contains("\\\"")) {
                        result = result.replace("\"", "\\\"");
                    }
                    updateParameter(result.getBytes(StandardCharsets.UTF_8));
                }
            }
        } else {
            byte[] newMessage = helpers.buildHttpMessage(helpers.analyzeRequest(txtInput.getText()).getHeaders(), result.getBytes(StandardCharsets.UTF_8));
            setPrettyMessage(newMessage);
        }
    }
}
