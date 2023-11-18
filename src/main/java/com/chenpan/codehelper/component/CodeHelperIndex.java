package com.chenpan.codehelper.component;

import com.chenpan.codehelper.bean.DBBaseBean;
import com.chenpan.codehelper.bean.DBPropertiesBean;
import com.chenpan.codehelper.bean.TableBean;
import com.chenpan.codehelper.common.AppCompatActivity;
import com.chenpan.codehelper.common.Bundle;
import com.chenpan.codehelper.config.SysConfigBean;
import com.chenpan.codehelper.manage.TableServiceManage;
import com.chenpan.codehelper.utils.JDBCUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description TODO
 * @Author chenpan
 * @Date 2023/11/17 14:22
 */
public class CodeHelperIndex extends AppCompatActivity {
    private JTree dbTree;
    private JPanel topJpanel;
    private JPanel paramJpanel;
    TableServiceManage tableServiceManage = new TableServiceManage();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.init();
    }

    /**
     * 做一些初始化的工作
     */
    private void init() {
        DBPropertiesBean dbPropertiesBean = SysConfigBean.getInstance().getDbPropertiesBean();
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(dbPropertiesBean.getDbName());

        DefaultMutableTreeNode tableGroup = new DefaultMutableTreeNode("表");
        DefaultMutableTreeNode viewGroup = new DefaultMutableTreeNode("视图");
        addTreeChildView(tableGroup);
        top.add(tableGroup);
        top.add(viewGroup);
        dbTree.setModel(new DefaultTreeModel(top));

    }


    private void addTreeChildView(DefaultMutableTreeNode tableGroup) {
        List<DBBaseBean> tableBeans = tableServiceManage.queryTableList();
    }

    @Override
    public JPanel getjPanel() {
        return topJpanel;
    }

    @Override
    public String getTitle() {
        return "程序员劝退师代码生成器";
    }

}
