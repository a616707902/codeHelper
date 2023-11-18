package com.chenpan.codehelper.common;

import com.chenpan.codehelper.CodeHelperStart;

import javax.swing.*;

/**
 * @Description 模仿android的顶层设计
 * @Author chenpan
 * @Date 2023/11/17 14:44
 */
public abstract class AppCompatActivity {

  public abstract  JPanel getjPanel();
  public abstract  String getTitle();
  public  void  startActivity(Intent intent){
    CodeHelperStart.startActivity(intent.context,intent.toContext);
    //生命周期方法
    intent.toContext.onCreate(intent.getBundle());
  }
  public  void onCreate(Bundle savedInstanceState){

  }
}
