package com.ruoyi.project.zxsd.store.util;

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
public class StoreCodeUtil {

    public static String getInviteCode(){
        Date nowdate = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmss");
        String nowdatextr = ft.format(nowdate);
        System.out.println(nowdatextr);
        Random r = new Random();
        int a = r.nextInt(99)+1;
        String a_str = String.valueOf(a);
        if(a_str.length()<2){
            a_str = "0"+a_str;
        }
        nowdatextr = "YQM"+nowdatextr+a_str;
        return nowdatextr;
    }
    public static String getStoreNo(){
        Date nowdate = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMdd");
        String nowdatextr = ft.format(nowdate);
        System.out.println(nowdatextr);
        Random r = new Random();
        int a = r.nextInt(999999)+1;
        String a_str = String.valueOf(a);
        while (a_str.length()<6){
            a_str = "0"+a_str;
        }
        nowdatextr = "S"+nowdatextr+a_str;
        return nowdatextr;
    }
}
