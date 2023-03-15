package ui;

import burp.BurpExtender;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import utils.AesUtil;
import utils.SM2Util;
import utils.SM4Util;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URLDecoder;
import java.util.Locale;

public class MainUI {
    private BurpExtender burpExtender;
    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private JPanel decodePanel;
    private JPanel exPanel;
    private JPanel cryptoPanel;
    private JPanel confPanel;
    private JButton decodeButton;
    private JButton encodeButton;
    private JComboBox cryptoTypeComboBox;
    private JComboBox panddingTypeComboBox;
    private JTextField keyTextField;
    private JTextField publicKeyTextField;
    private JTextArea sourceTextArea;
    private JTextArea cryptTextArea;
    private JTextField ivTextField;
    private JLabel secretLable;
    private JLabel publicLable;
    private JLabel ivLabel;
    private JLabel paddingLabel;
    private JLabel cryptTypeLabel;
    private JComboBox codeTypeComboBox;
    private JLabel codeTypeLabel;
    private JLabel reqCryptoTypeLabel;
    private JLabel reqPanddingLabel;
    private JLabel reqCodeTypeLabel;
    private JLabel reqIvLabel;
    private JLabel reqKeyLabel;
    private JLabel reqPublicKeyLabel;
    private JComboBox reqCryptoTypeBox;
    private JComboBox reqPanddingBox;
    private JComboBox reqCodeTypeBox;
    private JTextField reqIvField;
    private JTextField reqKeyField;
    private JTextField reqPublicKeyField;
    private JButton reqSaveConfButton;
    private JPanel textPanel;
    private JComboBox reqDataTypeBox;
    private JLabel reqDataTypeLabel;
    private JPanel requestPanel;
    private JPanel responsePanel;
    private JTextField reqParameterField;
    private JPanel resContenPanel;
    private JPanel resInfoPanel;
    private JPanel reqContentPanel;
    private JPanel reqInfoPanel;
    private JLabel resCryptoTypeLabel;
    private JComboBox resCryptoTypeBox;
    private JLabel resPanddingLabel;
    private JComboBox resPanddingBox;
    private JComboBox resCodeTypeBox;
    private JLabel resCodeTypeLabel;
    private JTextField resIvField;
    private JLabel resIvLabel;
    private JTextField resKeyField;
    private JLabel resKeyLabel;
    private JTextField resPublicKeyField;
    private JLabel resPublicKeyLabel;
    private JButton resSaveConfButton;
    private JLabel reqParameterLabel;
    private JTextArea codeTextArea;
    private JButton button1;
    private JButton button2;
    private JPanel codeEditorPanel;
    private JScrollPane codeScPanel;
    private RSyntaxTextArea codeArea;


    public MainUI() {
        Init();
    }

    public MainUI(BurpExtender burpExtender) {
        this.burpExtender = burpExtender;
        Init();
    }

    public void Init() {

        //编码工具初始化
        publicLable.setVisible(false);
        publicKeyTextField.setVisible(false);

        //请求配置默认初始化
        reqIvLabel.setVisible(false);
        reqIvField.setVisible(false);
        reqPublicKeyLabel.setVisible(false);
        reqPublicKeyField.setVisible(false);

        //响应配置默认初始化
        resIvLabel.setVisible(false);
        resIvField.setVisible(false);
        resPublicKeyLabel.setVisible(false);
        resPublicKeyField.setVisible(false);

        //编码工具，加密按钮添加事件监听
        encodeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch ((String) cryptoTypeComboBox.getSelectedItem()) {
                    case "AES":
                        try {
                            aesCrypto(true, URLDecoder.decode(sourceTextArea.getText(), "UTF-8").replace(" ", "+"));
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "请检查密钥或填充是否正确", "错误！", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "SM4":
                        try {
                            sm4Crypto(true, URLDecoder.decode(sourceTextArea.getText(), "UTF-8").replace(" ", "+"));
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "请检查密钥或填充是否正确", "错误！", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "SM2":
                        try {
                            sm2Crypto(true, URLDecoder.decode(sourceTextArea.getText(), "UTF-8").replace(" ", "+"));
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "请检查公私钥是否正确！！", "错误！", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                }
            }
        });

        //编码工具，解密按钮添加事件监听
        decodeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch ((String) cryptoTypeComboBox.getSelectedItem()) {
                    case "AES":
                        try {
                            aesCrypto(false, URLDecoder.decode(sourceTextArea.getText(), "UTF-8").replace(" ", "+"));
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "请检查密钥或填充是否正确", "错误！", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "SM4":
                        try {
                            sm4Crypto(false, URLDecoder.decode(sourceTextArea.getText(), "UTF-8").replace(" ", "+"));
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "请检查密钥或填充是否正确", "错误！", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "SM2":
                        try {
                            sm2Crypto(false, URLDecoder.decode(sourceTextArea.getText(), "UTF-8").replace(" ", "+"));
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "请检查公私钥是否正确！！", "错误！", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                }
            }
        });

        //编码工具，加密方式多选框添加监听
        cryptoTypeComboBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cryptoTypeComboBox.getSelectedItem().equals("SM2")) {
                    paddingLabel.setVisible(false);
                    panddingTypeComboBox.setVisible(false);
                    codeTypeLabel.setVisible(false);
                    codeTypeComboBox.setVisible(false);
                    ivLabel.setVisible(false);
                    ivTextField.setVisible(false);
                    publicLable.setVisible(true);
                    publicKeyTextField.setVisible(true);
                } else {
                    paddingLabel.setVisible(true);
                    panddingTypeComboBox.setVisible(true);
                    codeTypeLabel.setVisible(true);
                    codeTypeComboBox.setVisible(true);
                    ivLabel.setVisible(true);
                    ivTextField.setVisible(true);
                    publicLable.setVisible(false);
                    publicKeyTextField.setVisible(false);
                }
            }
        });

        //请求配置，加密多选框添加事件监听
        reqCryptoTypeBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reqCryptoTypeBox.getSelectedItem().equals("SM2")) {
                    reqPanddingLabel.setVisible(false);
                    reqPanddingBox.setVisible(false);
                    reqCodeTypeLabel.setVisible(false);
                    reqCodeTypeBox.setVisible(false);
                    reqIvLabel.setVisible(false);
                    reqIvField.setVisible(false);
                    reqPublicKeyLabel.setVisible(true);
                    reqPublicKeyField.setVisible(true);
                } else {
                    reqPanddingLabel.setVisible(true);
                    reqPanddingBox.setVisible(true);
                    reqCodeTypeLabel.setVisible(true);
                    reqCodeTypeBox.setVisible(true);
                    reqPublicKeyLabel.setVisible(false);
                    reqPublicKeyField.setVisible(false);
                    if (String.valueOf(reqPanddingBox.getSelectedItem()).startsWith("CBC")) {
                        reqIvLabel.setVisible(true);
                        reqIvField.setVisible(true);
                    }
                }
            }
        });

        //请求配置，填充多选框添加事件监听
        reqPanddingBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (String.valueOf(reqPanddingBox.getSelectedItem()).startsWith("CBC")) {
                    reqIvLabel.setVisible(true);
                    reqIvField.setVisible(true);
                } else {
                    reqIvLabel.setVisible(false);
                    reqIvField.setVisible(false);
                }
            }
        });

        //请求配置，保存配置按钮添加事件监听
        reqSaveConfButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                burpExtender.getConf().put("reqCryptoType", (String) reqCryptoTypeBox.getSelectedItem());
                burpExtender.getConf().put("reqCryptoPadding", (String) reqPanddingBox.getSelectedItem());
                burpExtender.getConf().put("reqCryptoCode", (String) reqCodeTypeBox.getSelectedItem());
                burpExtender.getConf().put("reqDataType", (String) reqDataTypeBox.getSelectedItem());
                burpExtender.getConf().put("reqCryptoIv", reqIvField.getText());
                burpExtender.getConf().put("reqCryptoKey", reqKeyField.getText());
                burpExtender.getConf().put("reqCryptoPublicKey", reqPublicKeyField.getText());
                burpExtender.getConf().put("reqParameter", reqParameterField.getText());
                burpExtender.getConf().put("isEnable", "open");
                JOptionPane.showMessageDialog(null, "配置保存成功", "保存成功", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //响应配置，加密多选框添加事件监听
        resCryptoTypeBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reqCryptoTypeBox.getSelectedItem().equals("SM2")) {
                    resPanddingLabel.setVisible(false);
                    resPanddingBox.setVisible(false);
                    resCodeTypeLabel.setVisible(false);
                    resCodeTypeBox.setVisible(false);
                    resIvLabel.setVisible(false);
                    resIvField.setVisible(false);
                    resPublicKeyLabel.setVisible(true);
                    resPublicKeyField.setVisible(true);
                } else {
                    resPanddingLabel.setVisible(true);
                    resPanddingBox.setVisible(true);
                    resCodeTypeLabel.setVisible(true);
                    resCodeTypeBox.setVisible(true);
                    resPublicKeyLabel.setVisible(false);
                    resPublicKeyField.setVisible(false);
                    if (String.valueOf(reqPanddingBox.getSelectedItem()).startsWith("CBC")) {
                        resIvLabel.setVisible(true);
                        resIvField.setVisible(true);
                    }
                }
            }
        });

        //响应配置，填充多选框添加事件监听
        resPanddingBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (String.valueOf(reqPanddingBox.getSelectedItem()).startsWith("CBC")) {
                    resIvLabel.setVisible(true);
                    resIvField.setVisible(true);
                } else {
                    resIvLabel.setVisible(false);
                    resIvField.setVisible(false);
                }
            }
        });

        //响应配置，保存配置按钮添加事件监听
        resSaveConfButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                burpExtender.getConf().put("resCryptoType", (String) reqCryptoTypeBox.getSelectedItem());
                burpExtender.getConf().put("resCryptoPadding", (String) reqPanddingBox.getSelectedItem());
                burpExtender.getConf().put("resCryptoCode", (String) reqCodeTypeBox.getSelectedItem());
                burpExtender.getConf().put("resDataType", (String) reqDataTypeBox.getSelectedItem());
                burpExtender.getConf().put("resCryptoIv", reqIvField.getText());
                burpExtender.getConf().put("resCryptoKey", reqKeyField.getText());
                burpExtender.getConf().put("resCryptoPublicKey", reqPublicKeyField.getText());
                burpExtender.getConf().put("isEnable", "open");
                JOptionPane.showMessageDialog(null, "配置保存成功", "保存成功", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //js扩展，代码框配置
        codeTextArea = new RSyntaxTextArea(20, 10);
        codeArea = (RSyntaxTextArea) codeTextArea;
        codeArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        LanguageSupportFactory.get().register(codeArea);
        codeArea.setMarkOccurrences(true);
        codeArea.setCodeFoldingEnabled(true);
        codeArea.setTabsEmulated(true);
        ToolTipManager.sharedInstance().registerComponent(codeArea);
        codeScPanel = new RTextScrollPane(codeArea);
        RTextScrollPane scrollPane = (RTextScrollPane) codeScPanel;
        scrollPane.setIconRowHeaderEnabled(true);
        scrollPane.getGutter().setBookmarkingEnabled(true);
        codeEditorPanel.add(scrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainUI");
        frame.setContentPane(new MainUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void aesCrypto(boolean isEncode, String data) {
        if (isEncode) {
            String result = AesUtil.Encrypt(data, keyTextField.getText(), (String) panddingTypeComboBox.getSelectedItem(), (String) codeTypeComboBox.getSelectedItem(), ivTextField.getText());
            cryptTextArea.setText(result);

        } else {
            String result = AesUtil.Decrypt(data, keyTextField.getText(), (String) panddingTypeComboBox.getSelectedItem(), ivTextField.getText());
            cryptTextArea.setText(result);
        }
    }

    public void sm4Crypto(boolean isEncode, String data) {
        if (isEncode) {
            String result = SM4Util.Encrypt(data, keyTextField.getText(), (String) panddingTypeComboBox.getSelectedItem(), (String) codeTypeComboBox.getSelectedItem(), ivTextField.getText());
            cryptTextArea.setText(result);

        } else {
            String result = SM4Util.Decrypt(data, keyTextField.getText(), (String) panddingTypeComboBox.getSelectedItem(), ivTextField.getText());
            cryptTextArea.setText(result);
        }
    }

    public void sm2Crypto(boolean isEncode, String data) {
        if (isEncode) {
            String result = SM2Util.Encrypt(data, publicKeyTextField.getText());
            cryptTextArea.setText(result);
        } else {
            String result = SM2Util.Decrypt(data, keyTextField.getText());
            cryptTextArea.setText(result);
        }
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1 = new JTabbedPane();
        rootPanel.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        decodePanel = new JPanel();
        decodePanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        decodePanel.setToolTipText("");
        tabbedPane1.addTab("解密配置", decodePanel);
        requestPanel = new JPanel();
        requestPanel.setLayout(new GridLayoutManager(13, 3, new Insets(10, 20, 0, 0), -1, -1));
        decodePanel.add(requestPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        reqContentPanel = new JPanel();
        reqContentPanel.setLayout(new GridLayoutManager(10, 3, new Insets(10, 0, 5, 0), -1, -1));
        requestPanel.add(reqContentPanel, new GridConstraints(1, 0, 10, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        reqCryptoTypeBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("AES");
        defaultComboBoxModel1.addElement("SM4");
        defaultComboBoxModel1.addElement("SM2");
        defaultComboBoxModel1.addElement("JSEx");
        reqCryptoTypeBox.setModel(defaultComboBoxModel1);
        reqCryptoTypeBox.setToolTipText("");
        reqContentPanel.add(reqCryptoTypeBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        reqContentPanel.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        reqPanddingBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("ECB/PKCS5Padding");
        defaultComboBoxModel2.addElement("ECB/ZeroPadding");
        defaultComboBoxModel2.addElement("CBC/PKCS5Padding");
        defaultComboBoxModel2.addElement("CBC/ZeroPadding");
        reqPanddingBox.setModel(defaultComboBoxModel2);
        reqContentPanel.add(reqPanddingBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reqKeyField = new JTextField();
        reqContentPanel.add(reqKeyField, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        reqCryptoTypeLabel = new JLabel();
        reqCryptoTypeLabel.setText("加解密方式：");
        reqContentPanel.add(reqCryptoTypeLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reqPanddingLabel = new JLabel();
        reqPanddingLabel.setText("填充： ");
        reqContentPanel.add(reqPanddingLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reqIvLabel = new JLabel();
        reqIvLabel.setText("偏移量：");
        reqContentPanel.add(reqIvLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reqKeyLabel = new JLabel();
        reqKeyLabel.setText("密钥：");
        reqContentPanel.add(reqKeyLabel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reqPublicKeyField = new JTextField();
        reqContentPanel.add(reqPublicKeyField, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        reqPublicKeyLabel = new JLabel();
        reqPublicKeyLabel.setText("公钥：");
        reqContentPanel.add(reqPublicKeyLabel, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reqIvField = new JTextField();
        reqContentPanel.add(reqIvField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        reqCodeTypeBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("Base64");
        defaultComboBoxModel3.addElement("HEX");
        reqCodeTypeBox.setModel(defaultComboBoxModel3);
        reqContentPanel.add(reqCodeTypeBox, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reqCodeTypeLabel = new JLabel();
        reqCodeTypeLabel.setText("编码方式：");
        reqContentPanel.add(reqCodeTypeLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        reqContentPanel.add(spacer2, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        reqSaveConfButton = new JButton();
        reqSaveConfButton.setText("保存配置");
        reqContentPanel.add(reqSaveConfButton, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reqDataTypeBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("Body");
        defaultComboBoxModel4.addElement("URL");
        reqDataTypeBox.setModel(defaultComboBoxModel4);
        reqContentPanel.add(reqDataTypeBox, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reqDataTypeLabel = new JLabel();
        reqDataTypeLabel.setText("数据类型：");
        reqContentPanel.add(reqDataTypeLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reqParameterField = new JTextField();
        reqParameterField.setText("");
        reqContentPanel.add(reqParameterField, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        reqParameterLabel = new JLabel();
        reqParameterLabel.setText("请求字段：");
        reqContentPanel.add(reqParameterLabel, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reqInfoPanel = new JPanel();
        reqInfoPanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        requestPanel.add(reqInfoPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.BOLD, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-39373));
        label1.setText("请求配置");
        reqInfoPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        reqInfoPanel.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("配置请求的加解密方式以及相关需求字段,JSON直接填KEY值即可,其中请求字段值如果为 <AllData> (不要尖括号)则为全部的POST数据，其余情况正常填写需加解密的字段");
        reqInfoPanel.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        reqInfoPanel.add(spacer4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        requestPanel.add(spacer5, new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        requestPanel.add(separator1, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        responsePanel = new JPanel();
        responsePanel.setLayout(new GridLayoutManager(3, 1, new Insets(10, 20, 0, 0), -1, -1));
        decodePanel.add(responsePanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        resContenPanel = new JPanel();
        resContenPanel.setLayout(new GridLayoutManager(8, 3, new Insets(10, 0, 0, 0), -1, -1));
        responsePanel.add(resContenPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        resCryptoTypeBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel5 = new DefaultComboBoxModel();
        defaultComboBoxModel5.addElement("AES");
        defaultComboBoxModel5.addElement("SM4");
        defaultComboBoxModel5.addElement("SM2");
        defaultComboBoxModel5.addElement("JSEx");
        resCryptoTypeBox.setModel(defaultComboBoxModel5);
        resCryptoTypeBox.setToolTipText("");
        resContenPanel.add(resCryptoTypeBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        resContenPanel.add(spacer6, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        resPanddingBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel6 = new DefaultComboBoxModel();
        defaultComboBoxModel6.addElement("ECB/PKCS5Padding");
        defaultComboBoxModel6.addElement("ECB/ZeroPadding");
        defaultComboBoxModel6.addElement("CBC/PKCS5Padding");
        defaultComboBoxModel6.addElement("CBC/ZeroPadding");
        resPanddingBox.setModel(defaultComboBoxModel6);
        resContenPanel.add(resPanddingBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resKeyField = new JTextField();
        resContenPanel.add(resKeyField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        resCryptoTypeLabel = new JLabel();
        resCryptoTypeLabel.setText("加解密方式：");
        resContenPanel.add(resCryptoTypeLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resPanddingLabel = new JLabel();
        resPanddingLabel.setText("填充： ");
        resContenPanel.add(resPanddingLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resIvLabel = new JLabel();
        resIvLabel.setText("偏移量：");
        resContenPanel.add(resIvLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resKeyLabel = new JLabel();
        resKeyLabel.setText("密钥：");
        resContenPanel.add(resKeyLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resPublicKeyField = new JTextField();
        resContenPanel.add(resPublicKeyField, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        resPublicKeyLabel = new JLabel();
        resPublicKeyLabel.setText("公钥：");
        resContenPanel.add(resPublicKeyLabel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resIvField = new JTextField();
        resContenPanel.add(resIvField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        resCodeTypeBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel7 = new DefaultComboBoxModel();
        defaultComboBoxModel7.addElement("Base64");
        defaultComboBoxModel7.addElement("HEX");
        resCodeTypeBox.setModel(defaultComboBoxModel7);
        resContenPanel.add(resCodeTypeBox, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resCodeTypeLabel = new JLabel();
        resCodeTypeLabel.setText("编码方式：");
        resContenPanel.add(resCodeTypeLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        resContenPanel.add(spacer7, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        resSaveConfButton = new JButton();
        resSaveConfButton.setText("保存配置");
        resContenPanel.add(resSaveConfButton, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resInfoPanel = new JPanel();
        resInfoPanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        responsePanel.add(resInfoPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, Font.BOLD, 16, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-39373));
        label3.setText("响应配置");
        resInfoPanel.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        resInfoPanel.add(spacer8, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("自定义配置响应内容的解密信息，此配置默认加解密所有响应内容的数据，如是json内的加密数据，请到解码工具解密");
        resInfoPanel.add(label4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        resInfoPanel.add(spacer9, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        responsePanel.add(spacer10, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        decodePanel.add(spacer11, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        exPanel = new JPanel();
        exPanel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("JS扩展配置", exPanel);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        exPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("JS扩展");
        panel1.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        panel1.add(spacer12, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        panel1.add(spacer13, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("在此直接使用JS代码，其中主要编写两个函数即可，即enCrypto以及deCrypto函数");
        panel1.add(label6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer14 = new Spacer();
        exPanel.add(spacer14, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        codeEditorPanel = new JPanel();
        codeEditorPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        exPanel.add(codeEditorPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        codeScPanel = new JScrollPane();
        codeEditorPanel.add(codeScPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        codeTextArea = new JTextArea();
        codeScPanel.setViewportView(codeTextArea);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        exPanel.add(panel2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        button1 = new JButton();
        button1.setText("Button");
        panel2.add(button1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer15 = new Spacer();
        panel2.add(spacer15, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        button2 = new JButton();
        button2.setText("Button");
        panel2.add(button2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cryptoPanel = new JPanel();
        cryptoPanel.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("解码工具", cryptoPanel);
        confPanel = new JPanel();
        confPanel.setLayout(new GridLayoutManager(6, 3, new Insets(5, 5, 0, 0), -1, -1));
        cryptoPanel.add(confPanel, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        cryptoTypeComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel8 = new DefaultComboBoxModel();
        defaultComboBoxModel8.addElement("AES");
        defaultComboBoxModel8.addElement("SM4");
        defaultComboBoxModel8.addElement("SM2");
        defaultComboBoxModel8.addElement("JSEx");
        cryptoTypeComboBox.setModel(defaultComboBoxModel8);
        cryptoTypeComboBox.setToolTipText("");
        confPanel.add(cryptoTypeComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer16 = new Spacer();
        confPanel.add(spacer16, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        panddingTypeComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel9 = new DefaultComboBoxModel();
        defaultComboBoxModel9.addElement("ECB/PKCS5Padding");
        defaultComboBoxModel9.addElement("ECB/ZeroPadding");
        defaultComboBoxModel9.addElement("CBC/PKCS5Padding");
        defaultComboBoxModel9.addElement("CBC/ZeroPadding");
        panddingTypeComboBox.setModel(defaultComboBoxModel9);
        confPanel.add(panddingTypeComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        keyTextField = new JTextField();
        confPanel.add(keyTextField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cryptTypeLabel = new JLabel();
        cryptTypeLabel.setText("加解密方式：");
        confPanel.add(cryptTypeLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        paddingLabel = new JLabel();
        paddingLabel.setText("填充： ");
        confPanel.add(paddingLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ivLabel = new JLabel();
        ivLabel.setText("偏移量：");
        confPanel.add(ivLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        secretLable = new JLabel();
        secretLable.setText("密钥：");
        confPanel.add(secretLable, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        publicKeyTextField = new JTextField();
        confPanel.add(publicKeyTextField, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        publicLable = new JLabel();
        publicLable.setText("公钥：");
        confPanel.add(publicLable, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ivTextField = new JTextField();
        confPanel.add(ivTextField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        codeTypeComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel10 = new DefaultComboBoxModel();
        defaultComboBoxModel10.addElement("Base64");
        defaultComboBoxModel10.addElement("HEX");
        codeTypeComboBox.setModel(defaultComboBoxModel10);
        confPanel.add(codeTypeComboBox, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        codeTypeLabel = new JLabel();
        codeTypeLabel.setText("编码方式：");
        confPanel.add(codeTypeLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer17 = new Spacer();
        cryptoPanel.add(spacer17, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textPanel = new JPanel();
        textPanel.setLayout(new GridLayoutManager(1, 3, new Insets(10, 20, 150, 20), -1, -1));
        cryptoPanel.add(textPanel, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        textPanel.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 300), null, 0, false));
        sourceTextArea = new JTextArea();
        sourceTextArea.setLineWrap(true);
        scrollPane1.setViewportView(sourceTextArea);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        textPanel.add(panel3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        decodeButton = new JButton();
        decodeButton.setText("解密");
        panel3.add(decodeButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        encodeButton = new JButton();
        encodeButton.setText("加密");
        panel3.add(encodeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        textPanel.add(scrollPane2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        cryptTextArea = new JTextArea();
        cryptTextArea.setEditable(false);
        cryptTextArea.setLineWrap(true);
        scrollPane2.setViewportView(cryptTextArea);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}

