package com.lt.utils;

import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.word.WordExportUtil;
import cn.afterturn.easypoi.word.entity.params.ExcelListEntity;
import com.lt.entity.vo.asset.RiskAssetVo4;
import com.lt.entity.vo.attacker.AttackerVo4;
import com.lt.entity.vo.victim.VictimVo3;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * word报告导出工具类
 *
 * @author teng.li
 * @version 1.0
 * @date 2020/8/20 19:07
 */
@Component
public class WordPicExportUtil {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");

    //输出word到本地磁盘路径
    private static String wordPath;

    //模板路径
    private static String templatePath;

    @Value(value = "${reportPath.word.wordPath}")
    public void setWordPath(String wordPath) {
        WordPicExportUtil.wordPath = wordPath;
    }


    @Value(value = "${reportPath.word.templatePath}")
    public void setTemplatePath(String templatePath) {
        WordPicExportUtil.templatePath = templatePath;
    }

    /**
     * word导出方法，需要传入包含图片路径的Map，Key是word里的图片名，value是图片URL
     *
     * @param picMap
     */
    public static void imageWordExport(LinkedHashMap<String, String> picMap) {
        Map<String, Object> map = new HashMap<>();
        map.put("department", "安恒信息");
        map.put("person", "安恒信息Hello World小队");
        map.put("time", format.format(new Date()));

        ImageEntity image = new ImageEntity();
        image.setHeight(300);
        image.setWidth(650);
        image.setType(ImageEntity.URL);
        image.setUrl("D:/ReportExport/Echart/test-bd5fe9e9.png");
        map.put("distributionTrend", image);

        ImageEntity image2 = new ImageEntity();
        image2.setHeight(300);
        image2.setWidth(650);
        image2.setType(ImageEntity.URL);
        image2.setUrl("D:/ReportExport/Echart/test-bd5fe9e9.png");
        map.put("RiskAsset", image2);

        ImageEntity image3 = new ImageEntity();
        image3.setHeight(300);
        image3.setWidth(650);
        image3.setType(ImageEntity.URL);
        image3.setUrl("D:/ReportExport/Echart/test-bd5fe9e9.png");
        map.put("RiskAssetTop5", image3);

        ImageEntity image4 = new ImageEntity();
        image4.setHeight(300);
        image4.setWidth(650);
        image4.setType(ImageEntity.URL);
        image4.setUrl("D:/ReportExport/Echart/test-bd5fe9e9.png");
        map.put("VictimTop5", image4);

        ImageEntity image5 = new ImageEntity();
        image5.setHeight(300);
        image5.setWidth(650);
        image5.setType(ImageEntity.URL);
        image5.setUrl("D:/ReportExport/Echart/test-bd5fe9e9.png");
        map.put("AttackerTop5", image5);

        ImageEntity image6 = new ImageEntity();
        image6.setHeight(300);
        image6.setWidth(650);
        image6.setType(ImageEntity.URL);
        image6.setUrl("D:/ReportExport/Echart/test-bd5fe9e9.png");
        map.put("KillChainTop5", image6);

        ImageEntity image7 = new ImageEntity();
        image7.setHeight(300);
        image7.setWidth(650);
        image7.setType(ImageEntity.URL);
        image7.setUrl("D:/ReportExport/Echart/test-bd5fe9e9.png");
        map.put("DeviceTop5", image7);

        ImageEntity image8 = new ImageEntity();
        image8.setHeight(300);
        image8.setWidth(650);
        image8.setType(ImageEntity.URL);
        image8.setUrl("D:/ReportExport/Echart/test-bd5fe9e9.png");
        map.put("ModelTop5", image8);

        ImageEntity image9 = new ImageEntity();
        image9.setHeight(300);
        image9.setWidth(650);
        image9.setType(ImageEntity.URL);
        image9.setUrl("D:/ReportExport/Echart/test-bd5fe9e9.png");
        map.put("SubCateGoryTop5", image9);

       /* //把所有图片路径放入Map
        for (Map.Entry<String, String> entry : picMap.entrySet()) {
            ImageEntity image = new ImageEntity();
            image.setHeight(300);
            image.setWidth(650);
            image.setType(ImageEntity.URL);
            image.setUrl(entry.getValue());
            map.put(entry.getKey(), image);
        }*/

        try {
            XWPFDocument doc = WordExportUtil.exportWord07(
                    templatePath, map);
            FileOutputStream fos = new FileOutputStream(wordPath + "安全日志报告" + System.currentTimeMillis() + ".docx");
            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接输出到Web
     *
     * @param picMap
     */
    public static void imageWordExportWeb(Map<String, List> list, Map<String, String> picMap, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("department", "安恒信息");
        map.put("person", "安恒信息Hello World小队");
        map.put("time", format.format(new Date()));
        map.put("Suggestion", picMap.get("Suggestions"));

        //把所有图片路径放入Map
        for (Map.Entry<String, String> entry : picMap.entrySet()) {
            ImageEntity image = new ImageEntity();
            image.setHeight(300);
            image.setWidth(650);
            image.setType(ImageEntity.URL);
            image.setUrl(entry.getValue());
            map.put(entry.getKey(), image);
        }

        List<RiskAssetVo4> riskList = list.get("riskList");
        map.put("riskList", new ExcelListEntity(riskList, RiskAssetVo4.class));

        List<VictimVo3> victimList = list.get("victimList");
        map.put("victimList", new ExcelListEntity(victimList, VictimVo3.class));

        List<AttackerVo4> attackerList = list.get("attackerList");
        map.put("attackerList", new ExcelListEntity(attackerList, AttackerVo4.class));


        try {
            XWPFDocument doc = WordExportUtil.exportWord07(
                    templatePath, map);
            // 配置文件下载
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文,设置要下载的文件的名称
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("安全日志报告" + System.currentTimeMillis() + ".docx", "UTF-8"));

            // 实现文件下载
            OutputStream os = response.getOutputStream();
            doc.write(os);
            os.flush();
            os.close();
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
