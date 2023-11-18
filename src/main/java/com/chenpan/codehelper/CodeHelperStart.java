package com.chenpan.codehelper;

import com.chenpan.codehelper.common.AppCompatActivity;
import com.chenpan.codehelper.component.DateConfig;
import com.chenpan.codehelper.utils.MixedForm;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

/**
 * @Description TODO
 * @Author chenpan
 * @Date 2023/11/15 17:08
 */
public class CodeHelperStart {

    static JFrame frame;

    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                WebLookAndFeel.install();
//                JFrame frame = new JFrame("数据库配置");
//                frame.setContentPane(new DateConfig().jPanel);
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.pack();
//                frame.setLocationRelativeTo(null);
//                frame.setVisible(true);
//            }
//        });
        // 亮色
//        FlatLightLaf.setup();
//// 暗色
        FlatDarkLaf.setup();
//// 基于 FlatLaf Light）看起来像 IntelliJ IDEA 2019.2+ 中的 IntelliJ 主题
//        FlatIntelliJLaf.setup();
//// （基于 FlatLaf Dark）看起来像来自 IntelliJ IDEA 2019.2+
//        FlatDarculaLaf.setup();
//
//        FlatMacLightLaf.setup();
//        FlatMacDarkLaf.setup();

        UIManager.put("TextComponent.arc", 5);
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Component.innerFocusWidth", 1);
        UIManager.put("Button.innerFocusWidth", 1);
        UIManager.put("TitlePane.unifiedBackground", true);
        UIManager.put("TitlePane.menuBarEmbedded", false);

// 设置字体，设置字体抗锯齿
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        Font fontUIResource = new Font("宋体", Font.PLAIN, 20);
        for (Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontUIResource);
            }
        }
        UIManager.put("defaultFont", fontUIResource);
        frame = new JFrame("数据库配置");
        frame.setSize(860,540);
        frame.setContentPane(new DateConfig().getjPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void startActivity(AppCompatActivity fromActivity, AppCompatActivity toActivity) {
        frame.setTitle(toActivity.getTitle());
        frame.setContentPane(toActivity.getjPanel());
        frame.setVisible(true);
    }
}
