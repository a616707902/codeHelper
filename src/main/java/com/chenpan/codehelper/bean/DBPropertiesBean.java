package com.chenpan.codehelper.bean;

import com.alibaba.druid.DbType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @Description 用来存储数据库信息
 * @Author chenpan
 * @Date 2023/11/16 16:15
 */
@Data
public class DBPropertiesBean  implements Serializable  , Comparable<DBPropertiesBean>{
    private DbType dbType;
    private String userName;
    private String password;
    private String dateSourceUrl;
    private String ip;
    private String port;
    private String dbName;
    private Date createDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBPropertiesBean that = (DBPropertiesBean) o;
        return dbType == that.dbType && Objects.equals(userName, that.userName) && Objects.equals(password, that.password) && Objects.equals(ip, that.ip) && Objects.equals(port, that.port) && Objects.equals(dbName, that.dbName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dbType, userName, password, ip, port, dbName);
    }

    @Override
    public int compareTo(DBPropertiesBean o) {
        return  createDate.compareTo(o.getCreateDate());
    }
}
