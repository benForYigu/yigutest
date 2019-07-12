package com.thinkgem.jeesite.modules.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 商品工具类
 */
public class GoodsUtil {

    private static int sequence = 0;
    private static int length = 6;
    //自定义编号
    public static String biaoHao(){
        sequence = sequence >= 999999 ? 1 : sequence + 1;
        String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String s = Integer.toString(sequence);
        return datetime +addLeftZero();
    }

    public static String addLeftZero() {
        Random random = new Random();
        String st = "";
        for(int i=0;i<6;i++){
            int s = random.nextInt(9) % (9 - 0 + 1) + 0;
            st = st + String.valueOf(s);
        }
        return st;
    }

}
