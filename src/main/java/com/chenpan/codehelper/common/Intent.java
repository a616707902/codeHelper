package com.chenpan.codehelper.common;

import lombok.Data;

/**
 * @Description 模仿android的跳转
 * @Author chenpan
 * @Date 2023/11/17 14:42
 */
@Data
public class Intent implements  Cloneable{
    Bundle bundle;
    AppCompatActivity  context;
    AppCompatActivity  toContext;
    public Intent setClass(AppCompatActivity appCompatActivity,Class<? extends AppCompatActivity> classz){
        this.context=appCompatActivity;
        try {
            this.toContext=classz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
}
