package com.lt.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/23 14:05
 */
@Data
public class ResultMapUtil {
    public static Map<String, Object> map=new HashMap<>();

    public static void method(String time, Object o){
        map.put(time,o);
    }
}
