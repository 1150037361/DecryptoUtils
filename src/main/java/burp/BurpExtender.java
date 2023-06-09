package burp;

import lombok.Data;
import ui.MainUI;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.HashMap;

@Data
public class BurpExtender implements IBurpExtender, ITab, IMessageEditorTabFactory{
    private IBurpExtenderCallbacks callbacks;
    private boolean isEnable = true;
    private IExtensionHelpers helpers;
    private PrintWriter stdout;
    private JPanel rootPanel;
    private MainUI ui;
    private HashMap<String, String> conf = new HashMap<>();

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        conf.put("isEnable", "close");
        stdout = new PrintWriter(callbacks.getStdout(), true);
        stdout.println("-------------------------------\n");
        stdout.println("[+] Author: against\n");
        stdout.println("[+] ExtenderName: DecryptoUtils\n");
        stdout.println("-------------------------------\n");

        callbacks.registerMessageEditorTabFactory(this);
        buildUI();
    }

    public void buildUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ui = new MainUI(BurpExtender.this);
                rootPanel = new JPanel();

                rootPanel.setLayout(new BoxLayout(rootPanel, 1));
                rootPanel.add(ui.$$$getRootComponent$$$());
                //callbacks.customizeUiComponent(rootPanel);
                callbacks.addSuiteTab(BurpExtender.this);
            }
        });
    }

    @Override
    public String getTabCaption() {
        return "DecryptoUtils";
    }

    @Override
    public Component getUiComponent() {
        return rootPanel;
    }

    @Override
    public IMessageEditorTab createNewInstance(IMessageEditorController controller, boolean editable) {
        return new DecodeTab(controller, editable, this);
    }

    public void printLog(String data) {
        stdout.println(data + "\n");
    }
}
