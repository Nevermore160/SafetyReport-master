package com.lt.utils;

/**
 * 字符串匹配类
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 16:51
 */
public class IsMatchUtil {

    /**
     * 把 \\\\n 替换成 \n 在前端实现自动换行
     * @param str
     * @return
     */
    public static String isMatch(String str){
        String string1 = str.replaceAll("\\\\n","\n");
        String s = string1.replaceAll("-", "•");
        return s;
    }

    /**
     * 把 \r\n 替换成  \r\n 在Word实现自动换行
     * @param str
     * @return
     */
    public static String isMatch1(String str){
        String string1 = str.replaceAll("\n","\r\n");
        return string1;
    }
}
