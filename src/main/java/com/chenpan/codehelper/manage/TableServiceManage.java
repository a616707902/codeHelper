package com.chenpan.codehelper.manage;

import com.chenpan.codehelper.bean.DBBaseBean;
import com.chenpan.codehelper.bean.TableBean;
import com.chenpan.codehelper.bean.ViewBean;
import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @Author chenpan
 * @Date 2023/11/17 17:21
 */
@Data
public class TableServiceManage {
    /**
     *查询表数据
     */
    public List<DBBaseBean> queryTableList() {
        return null;
    }

    /**
     * 查询视图列表
     * @return
     */
    public List<DBBaseBean> queryViewList() {
        return null;
    }
}
