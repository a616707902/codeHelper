package com.chenpan.codehelper.utils;

import javax.swing.*;
import java.awt.*;

/**
 * 混合布局用于展示单表，多表
 */
public class MixedForm extends JFrame {
    private GridPanel gridPanel;//自定义的格子面板对象
    private NullPanel nullPanel;//自定义的空布局面板对象
    private JTabbedPane tabbedPane;//选项卡面板
    JMenuBar menuBar;//菜单条
    JMenu menu1,menu2,submenu;//菜单
    JMenuItem item1,item2;//菜单项
    public MixedForm() {
        setTitle("程序员劝退师代码生成器");
        menuBar =new JMenuBar();//实例化菜单条
        menu1=new JMenu("菜单1");//实例化菜单
        menu2=new JMenu("菜单2");//同上
        menuBar.add(menu1);//菜单条中添加菜单
        menuBar.add(menu2);//同上
        setJMenuBar(menuBar);//将该菜单条设置给这个窗口
        gridPanel = new GridPanel();//实例化格子面板对象
        nullPanel = new NullPanel();//实例化空布局面板对象
        tabbedPane = new JTabbedPane();//实例化选项卡面板
        //将两个自定义的面板加入到选项卡面板下，通过选项卡可进行切换
        tabbedPane.add("格子布局面板", gridPanel);
        tabbedPane.add("空布局面板", nullPanel);
        //设置这个MixedForm的布局模式为BorderLayout
        setLayout(new BorderLayout());
        //将这个选项卡面板添加入该MixedForm的中区域
        add(tabbedPane, BorderLayout.CENTER);
        //随便填充几个，将东南西北填充完
        add(new JButton("东"), BorderLayout.EAST);
        add(new JButton("南"), BorderLayout.SOUTH);
        add(new JButton("西"), BorderLayout.WEST);
        add(new JButton("北"), BorderLayout.NORTH);
        //设置MixedForm的相关属性
        setBounds(10, 10, 570, 390);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}