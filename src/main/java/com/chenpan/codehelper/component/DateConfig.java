package com.chenpan.codehelper.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.DbType;
import com.chenpan.codehelper.config.SysConfigBean;
import com.chenpan.codehelper.bean.DBPropertiesBean;
import com.chenpan.codehelper.common.AppCompatActivity;
import com.chenpan.codehelper.common.Intent;
import com.chenpan.codehelper.manage.DateConfigManage;
import com.chenpan.codehelper.utils.JDBCUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description TODO
 * @Author chenpan
 * @Date 2023/11/16 10:49
 */
@Slf4j
public class DateConfig extends AppCompatActivity {
    private JTextField ipTextField;
    private JButton ipOther;

    private JTextField portTextField;
    private JTextField dbTextField;
    private JTextArea urlTextArea;
    private JRadioButton mysqlRadioButton;
    private JRadioButton oracleRadioButton;
    private JButton testButton;
    private JButton cancelButton;

    public JPanel getjPanel() {
        return jPanel;
    }

    @Override
    public String getTitle() {
        return "数据库配置";
    }

    private JPanel jPanel;
    private JLabel ipLabel;
    private JLabel nameLabel;
    private JLabel passwdLabel;
    private JTextField nameTextField;
    private JLabel portLabel;
    private JLabel dbLabel;
    private JLabel urlLabel;
    private JLabel dbTypeLabel;
    private JButton connectionButton;
    private JPasswordField passwordField;
    private String dbMysqlUrl = "jdbc:mysql://%s:%s/%s?\n" +
            "characterEncoding=UTF-8&useUnicode=true&useSSL=false&\n" +
            "tinyInt1isBit=false&allowPublicKeyRetrieval=true&\n" +
            "serverTimezone=Asia/Shanghai";
    private String dbOracleUrl = "jdbc:oracle:thin:@%s:%s:%s";
    private String urlLink = null;
    DbType dbType = DbType.mysql;
    DBPropertiesBean showDb = null;
    DateConfigManage dateConfigManage = new DateConfigManage();

    FocusListener textFiedListener = new FocusListener() {
        public void focusGained(FocusEvent e) {
        }

        public void focusLost(FocusEvent e) {
            //只要是修改了  连接都先关闭
            connectionButton.setEnabled(false);
            Object source = e.getSource();
            if (source == ipTextField || source == portTextField || source == dbTextField) {
                reflashUrlLink();
            } else if (source == nameTextField) {
                showDb.setUserName(nameTextField.getText().trim());
            } else if (source == passwordField) {
                String text = passwordField.getText();
                showDb.setPassword(text);
            }

        }
    };

    /**
     * 刷新urlLink,并展示到界面上
     */
    private void reflashUrlLink() {
        String ipTextFieldText = ipTextField.getText();
        String portTextFieldText = portTextField.getText();
        if (dbType == DbType.mysql) {
            urlLink = String.format(dbMysqlUrl, ipTextFieldText, portTextFieldText, dbTextField.getText());
        } else {
            urlLink = String.format(dbOracleUrl, ipTextFieldText, portTextFieldText, dbTextField.getText());
        }
        urlTextArea.setText(urlLink);
        showDb.setDateSourceUrl(urlLink.replace("\n", "").trim());
        showDb.setDbType(this.dbType);
    }

    public DateConfig() {
        // 加载配置文件中的数据
        this.init();
        //初始化设置
        reflashUrlLink();
        //给该有的组件添加监听
        addCommponetListener();
    }

    private void init() {
        connectionButton.setEnabled(false);
        DBPropertiesBean defaultConfig = dateConfigManage.getDefaultConfig();
        showDb = new DBPropertiesBean();
        if (defaultConfig == null) {
            showDb.setDateSourceUrl(urlTextArea.getText().replace("\n", "").trim());
            showDb.setUserName(nameTextField.getText().trim());
            showDb.setPassword(passwordField.getText().trim());
            showDb.setDbType(this.dbType);
        } else {
            BeanUtil.copyProperties(defaultConfig, showDb);
            resetCommpent();
        }


    }

    private void addCommponetListener() {
        //添加焦点监听
        nameTextField.addFocusListener(textFiedListener);
        ipTextField.addFocusListener(textFiedListener);
        passwordField.addFocusListener(textFiedListener);
        portTextField.addFocusListener(textFiedListener);
        dbTextField.addFocusListener(textFiedListener);

        ipOther.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<DBPropertiesBean> dbPropertiesBeanList = dateConfigManage.getDbConfigList();
                createPopupMenu(dbPropertiesBeanList, e);
            }
        });
        testButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    JDBCUtils.init(showDb);
                    Connection connection = JDBCUtils.getConnection();
                    if (connection != null) {
                        JOptionPane.showMessageDialog(DateConfig.this.jPanel, "连接成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                        JDBCUtils.close(null, connection);
                        connectionButton.setEnabled(true);
                        showDb.setIp(ipTextField.getText().trim());
                        showDb.setPort(portTextField.getText().trim());
                        showDb.setDbName(dbTextField.getText().trim());
                        showDb.setCreateDate(DateUtil.date());
                        //测试成功 保存记录
                        dateConfigManage.addThisConnectMsgToFile(showDb);

                    } else {
                        JOptionPane.showMessageDialog(DateConfig.this.jPanel, "数据库连接失败", "提示", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JDBCUtils.closeDataSource();
                    JOptionPane.showMessageDialog(DateConfig.this.jPanel, "创建连接失败，请检查配置", "提示", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JDBCUtils.closeDataSource();
                    JOptionPane.showMessageDialog(DateConfig.this.jPanel, "创建连接失败，请检查配置", "提示", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        connectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                log.error("点击了连接");
                //保存系统运用的数据库信息
                SysConfigBean sysConfigBean = SysConfigBean.getInstance();
                sysConfigBean.setDbPropertiesBean(showDb);
                //跳转
                Intent intent = new Intent();
                intent.setClass(DateConfig.this, CodeHelperIndex.class);
                startActivity(intent);
            }
        });
        mysqlRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dbType = DbType.mysql;
                reflashUrlLink();
            }
        });
        oracleRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dbType = DbType.oracle;
                reflashUrlLink();
            }
        });
    }

    /**
     * 根据数据创建弹出菜单
     *
     * @param dbPropertiesBeanList
     * @param e
     */
    private void createPopupMenu(List<DBPropertiesBean> dbPropertiesBeanList, ActionEvent e) {
        if (CollectionUtil.isNotEmpty(dbPropertiesBeanList)) {
            JPopupMenu jPopupMenuOne = new JPopupMenu();
            dbPropertiesBeanList.stream().forEach(dbPropertiesBean -> {
                String name = dbPropertiesBean.getDbType().name() + ":" + dbPropertiesBean.getIp() + ":" + dbPropertiesBean.getPort() + "/" + dbPropertiesBean.getDbName();
                JRadioButtonMenuItem dbMenu = new JRadioButtonMenuItem(name);
                dbMenu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showDb = dbPropertiesBean;
                        resetCommpent();
                    }
                });
                jPopupMenuOne.add(dbMenu);
            });
            jPopupMenuOne.addSeparator();
            JMenuItem exit = new JMenuItem("退出");
            //将exit添加到jPopupMenuOne中
            jPopupMenuOne.add(exit);
            jPopupMenuOne.show(ipOther, ipOther.getX(), ipOther.getY());
        }
    }

    /**
     * 重置显示
     */
    private void resetCommpent() {
        ipTextField.setText(showDb.getIp());
        portTextField.setText(showDb.getPort());
        nameTextField.setText(showDb.getUserName());
        passwordField.setText(showDb.getPassword());
        urlTextArea.setText(showDb.getDateSourceUrl());
        dbTextField.setText(showDb.getDbName());
        if (showDb.getDbType() == DbType.mysql) {
            mysqlRadioButton.setSelected(true);
        } else {
            oracleRadioButton.setSelected(true);
        }
    }
}
