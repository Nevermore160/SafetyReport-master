package com.lt.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.UUID;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/20 10:41
 */
@Component
public class EchartsGenerateUtil {

    //JS路径、图片存放路径、Json存放路径
    private static String JSPath;
    private static String PicPath;
    private static String JsonPath;

    @Value(value = "${reportPath.echart.jsPath}")
    public void setJSPath(String JSPath) {
        this.JSPath = JSPath;
    }

    @Value(value = "${reportPath.echart.picPath}")
    public void setPicPath(String picPath) {
        this.PicPath = picPath;
    }

    @Value(value = "${reportPath.echart.jsonPath}")
    public void setJsonPath(String jsonPath) {
        this.JsonPath = jsonPath;
    }

    public static String generateEChart(String options) {
        //把json写入文件
        String dataPath = writeFile(options);
        //声明图片名
        String fileName = "report-" + UUID.randomUUID() + ".png";
        String path = PicPath + fileName;
        try {
            File file = new File(path);     //文件路径（路径+文件名）
            if (!file.exists()) {           //文件不存在则创建文件，先创建目录
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            String cmd = "phantomjs " + JSPath + " -infile " + dataPath + " -outfile " + path;
            //执行cmd
            Runtime.getRuntime().exec(cmd);

            //打印出cmd命令结果
            /*BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();*/

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return path;
        }
    }

    /**
     * 把echart json写入txt
     *
     * @param options
     * @return
     */
    public static String writeFile(String options) {
        String dataPath = JsonPath + UUID.randomUUID() + ".json";
        try {
            /* 写入Txt文件 */
            File writeName = new File(dataPath); // 相对路径，如果没有则要建立一个新的output.txt文件
            if (!writeName.exists()) {   //文件不存在则创建文件，先创建目录
                File dir = new File(writeName.getParent());
                dir.mkdirs();
                writeName.createNewFile(); // 创建新文件
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(writeName));
            out.write(options); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataPath;
    }
}