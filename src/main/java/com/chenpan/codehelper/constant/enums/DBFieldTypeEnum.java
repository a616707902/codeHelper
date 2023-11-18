package com.chenpan.codehelper.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库成员信息枚举
 */
@AllArgsConstructor
@Getter
public enum DBFieldTypeEnum {
    TABLE(1,"表",""),
    VIEW(2,"视图","");
    private  Integer code;
    private  String name;
    private  String sqlStr;

}
