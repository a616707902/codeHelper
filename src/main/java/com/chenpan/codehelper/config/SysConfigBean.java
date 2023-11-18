package com.chenpan.codehelper.config;

import com.chenpan.codehelper.bean.DBPropertiesBean;
import lombok.Data;

/**
 * @Description 系统配置类
 * @Author chenpan
 * @Date 2023/11/16 18:25
 */
@Data
public class SysConfigBean {
    private DBPropertiesBean dbPropertiesBean;
    private static SysConfigBean instance;
    private SysConfigBean() {

    }

    public static synchronized SysConfigBean getInstance() {

        if (instance == null) {

            instance = new SysConfigBean();

        }

        return instance;

    }
}
