package com.ruoyi.project.zxsd.sys.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 校验工具类
 *
 * @author wangdong
 * @date 2020.05.03
 */
@Slf4j
@Component
public class SystemCodeUtil {

    public static String getMenuCode(){
        //获取当前时间
        Date nowdate = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmssSS");
        String nowdatextr = ft.format(nowdate);
        System.out.println(nowdatextr);
        Random r = new Random();
        int a = r.nextInt(99)+1;
        String a_str = String.valueOf(a);
        if(a_str.length()<2){
            a_str = "0"+a_str;
        }
        nowdatextr = nowdatextr+a_str;
        return nowdatextr;
    }
    public static String getUserCode(){
        //获取当前时间
        Date nowdate = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmssSS");
        String nowdatextr = ft.format(nowdate);
        System.out.println(nowdatextr);
        Random r = new Random();
        int a = r.nextInt(99)+1;
        String a_str = String.valueOf(a);
        /**
         * 补零操作
         */
        if(a_str.length()<2){
            a_str = "0"+a_str;
        }
        nowdatextr = nowdatextr+a;
        return nowdatextr;
    }

    public static String getDriverNo(){
        //获取当前时间
        Date nowdate = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssSS");
        String nowdatextr = ft.format(nowdate);
        System.out.println(nowdatextr);
        Random r = new Random();
        int a = r.nextInt(99)+1;
        String a_str = String.valueOf(a);
        if(a_str.length()<2){
            a_str = "0"+a_str;
        }
        nowdatextr = "SJ"+nowdatextr+a_str;
        return nowdatextr;
    }

    public static void main(String[] args) {
        Random r = new Random();
        int a = r.nextInt(99)+1;
        System.out.println(a);
    }

}
