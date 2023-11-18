package com.chenpan.codehelper.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.chenpan.codehelper.bean.DBPropertiesBean;
import com.chenpan.codehelper.constant.SysConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @Description 数据库配置的服务类
 * @Author chenpan
 * @Date 2023/11/16 18:18
 */
@Slf4j
public class DateConfigManage {
    private String dateConfigPath = SysConstant.userDir + File.separator + SysConstant.configDir + File.separator + SysConstant.dbConfigFile;

    private List<DBPropertiesBean> dbList;

    public void addThisConnectMsgToFile(DBPropertiesBean showDb) {
        if (CollectionUtil.isNotEmpty(dbList)) {
            AtomicBoolean isRepeat= new AtomicBoolean(false);
            dbList.stream().forEach(dbPropertiesBean -> {
                if (dbPropertiesBean.equals(showDb)) {
                    isRepeat.set(true);
                    dbPropertiesBean.setCreateDate(showDb.getCreateDate());
                }
            });
            if (!isRepeat.get()){
                DBPropertiesBean addBean=new DBPropertiesBean();
                BeanUtil.copyProperties(showDb,addBean);
                dbList.add(addBean);
            }
            dbList = dbList.stream().distinct().collect(Collectors.toList());

        } else {
            dbList = new ArrayList<>();
            //以防修改showDb
            DBPropertiesBean addBean=new DBPropertiesBean();
            BeanUtil.copyProperties(showDb,addBean);
            dbList.add(addBean);
        }

        writeConfigToFile();

    }

    private void writeConfigToFile() {
        File file = FileUtil.file(dateConfigPath);
        if (!file.exists()) {
            try {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {
                log.error("DBconfig文件创建失败");
                throw new RuntimeException(e);
            }
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            //将List转换成数组
            DBPropertiesBean[] obj = new DBPropertiesBean[dbList.size()];
            dbList.toArray(obj);
            //执行序列化存储
            out.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DBPropertiesBean getDefaultConfig() {
      List<DBPropertiesBean>  configListByFile = readConfigListByFile();
        if (CollectionUtil.isNotEmpty(configListByFile)) {
            dbList=   configListByFile.stream().sorted(Comparator.comparing(DBPropertiesBean::getCreateDate).reversed()).collect(Collectors.toList());
            return dbList.get(0);
        }
        return null;
    }

    private List<DBPropertiesBean> readConfigListByFile() {
        File file = FileUtil.file(dateConfigPath);
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream out = new ObjectInputStream(new FileInputStream(file))) {
            //执行反序列化读取
            DBPropertiesBean[] obj = (DBPropertiesBean[]) out.readObject();
            //将数组转换成List
            List<DBPropertiesBean> listObject = Arrays.asList(obj);
            return listObject;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DBPropertiesBean> getDbConfigList() {
        //根据时间排序，最后编辑的在最前面
        dbList=   dbList.stream().sorted(Comparator.comparing(DBPropertiesBean::getCreateDate).reversed()).collect(Collectors.toList());
        return dbList;
    }
}
