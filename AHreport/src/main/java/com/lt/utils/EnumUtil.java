package com.lt.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 国家转换类,把国家中文字符串转换为英文字符串
 * @author teng.li
 * @version 1.0
 * @date 2020/8/18 10:36
 */
public class EnumUtil {

    public static Map<String, String> countryToCode() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("德国","Germany");
        map.put("中国","China");
        map.put("美国","United States");
        map.put("印度尼西亚","Indonesia");
        map.put("欧盟","欧盟");
        map.put("荷兰","Netherlands");
        map.put("加拿大","Canada");
        map.put("墨西哥","Mexico");
        map.put("日本","Japan");
        map.put("巴西","Brazil");
        map.put("乌克兰","Ukraine");
        map.put("韩国","Dem. Rep. Korea");
        map.put("西班牙","Spain");
        map.put("俄罗斯","Russia");
        map.put("泰国","Thailand");
        map.put("瑞典","Sweden");
        map.put("马来西亚","Malaysia");
        map.put("突尼斯","Tunisia");
        map.put("阿根廷","Argentina");
        map.put("尼日尼亚","尼日尼亚");
        map.put("越南","Vietnam");
        map.put("菲律宾","Philippines");
        map.put("南非","South Africa");
        map.put("罗马尼亚","Romania");
        map.put("意大利","Italy");
        map.put("英国","England");
        map.put("新加坡","Singapore");
        map.put("约旦","约旦");
        map.put("波兰","Poland");
        map.put("斯里兰卡","Sri Lanka");
        map.put("印度","India");
        map.put("亚太地区","亚太地区");
        map.put("法国","France");
        map.put("立陶宛","Lithuania");
        map.put("土耳其","Turkey");
        map.put("新西兰","New Zealand");

        return map;
    }

    public static Map<String, String> killChainToChar() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("KC_Reconnaissance","侦查");
        map.put("KC_Delivery","投递");
        map.put("KC_Exploitation","利用");
        map.put("KC_CommandControl","命令控制");
        map.put("KC_InternalRecon","内部侦查");
        map.put("KC_LateralMov","横向渗透");
        map.put("KC_Profit","获利");
        map.put("KC_Others","无");
        return map;
    }


//    public static Map<String, String> countryToCode() throws IOException {
//        Map<String, String> map1 = new HashMap<String, String>();
//        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/gj.txt");
//        BufferedReader br = new BufferedReader(new InputStreamReader(
//                inputStream));
//        String str = "";
//        for (String line = br.readLine(); line != null; line = br.readLine()) {
//            str += line;
//        }
//        String str1;
//        String[] split = str.split(",");
//        for (int i = 0; i < split.length; i++) {
//            str1 = split[i].substring(1, split[i].length() - 1);
//            String[] split1 = str1.split("\":\"");
//            map1.put(split1[0], split1[1]);
//        }
//        Map<String,String> map = new HashMap<String, String>();
//        for (Map.Entry<String, String> entry : map1.entrySet()) {
//            map.put(entry.getValue(),entry.getKey());
//        }
//        return map;
//    }
//
//    public static Map<String, String> killChainToChar() throws IOException {
//        Map<String, String> map1 = new HashMap<String, String>();
//        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/killChain.txt");
//        BufferedReader br = new BufferedReader(new InputStreamReader(
//                inputStream));
//        String str = "";
//        for (String line = br.readLine(); line != null; line = br.readLine()) {
//            str += line;
//        }
//        String str1;
//        String[] split = str.split(",");
//        for (int i = 0; i < split.length; i++) {
//            str1 = split[i].substring(1, split[i].length() - 1);
//            String[] split1 = str1.split("\":\"");
//            map1.put(split1[0], split1[1]);
//        }
//        Map<String,String> map = new HashMap<String, String>();
//        for (Map.Entry<String, String> entry : map1.entrySet()) {
//            map.put(entry.getValue(),entry.getKey());
//        }
//        return map;
//    }

    public static void main(String[] args) {
        Map<String, String> stringStringMap = null;
        try {
            stringStringMap = countryToCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, String> entry : stringStringMap.entrySet()) {
            System.out.println(entry.getKey() + "-" + entry.getValue());
        }
    }
}
