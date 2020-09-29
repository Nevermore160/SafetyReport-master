package com.lt.service.impl;

import com.alibaba.fastjson.JSON;
import com.lt.entity.vo.*;
import com.lt.entity.vo.asset.RiskAssetVo4;
import com.lt.entity.vo.attacker.AttackerVo3;
import com.lt.entity.vo.attacker.AttackerVo4;
import com.lt.entity.vo.suggestions.SuggestionVo1;
import com.lt.entity.vo.victim.VictimVo3;
import com.lt.service.*;
import com.lt.utils.*;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/20 20:34
 */
@Service
public class ReportDownloadServiceImpl implements ReportDownloadService {

    @Autowired
    private AttackerTopService attackerTopService;

    @Autowired
    private AttackerService attackerService;

    @Autowired
    private SuggestionService suggestionService;

    @Autowired
    private DistributionTrendService distributionTrendService;

    @Autowired
    private RiskAssetService riskAssetService;

    @Autowired
    private VictimService victimService;

    /**
     * 生成Word报告过程:
     * 1、从缓存拿到数据
     * 2、FreeMarkerUtil把数据填入ftl模板,获得option
     * 3、将option传入EchartsGenerateUtil生成图片，图片名和路径存入Map
     * 4、将Map传入WordExportUtil渲染出Word，输出到Response的输出流
     *
     * @param response
     */
    @Override
    public void wordExport(HttpServletResponse response) {
        //图表写入Word模板
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap();
        ConcurrentHashMap<String, List> listMap = new ConcurrentHashMap<>();

        CountDownLatch countDownLatch = new CountDownLatch(5);

        //攻击时序分布
        RespDTVo respDTVo = (RespDTVo) ResultMapUtil.map.get("respDTVo");
        // 模板参数
        HashMap<String, Object> freeMarkerFields = new HashMap<>();
        freeMarkerFields.put("list", JSON.toJSONString(respDTVo.getList()));
        freeMarkerFields.put("low", JSON.toJSONString(respDTVo.getLow()));
        freeMarkerFields.put("medium", JSON.toJSONString(respDTVo.getMedium()));
        freeMarkerFields.put("high", JSON.toJSONString(respDTVo.getHigh()));
        String option = null;
        try {
            option = FreemarkerUtil.generateString("DistributionTrend.ftl", "/temp", freeMarkerFields);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        concurrentHashMap.put("distributionTrend", EchartsGenerateUtil.generateEChart(option));


        //风险资产
        new Thread(new Runnable() {
            @Override
            public void run() {
                RespAssetVo respAssetVo = (RespAssetVo) ResultMapUtil.map.get("respAssetVo");

                HashMap<String, Object> freeMarkerFields = new HashMap<>();
                freeMarkerFields = new HashMap<>();
                freeMarkerFields.put("list", JSON.toJSONString(respAssetVo.getRiskAssetVo2().getList()));
                freeMarkerFields.put("titleName", JSON.toJSONString(respAssetVo.getRiskAssetVo3().getTitleName()));
                freeMarkerFields.put("data", JSON.toJSONString(respAssetVo.getRiskAssetVo3().getData()));

                LinkedList<LinkedList> data = (LinkedList<LinkedList>) respAssetVo.getRiskAssetVo1().getData();

                ArrayList<RiskAssetVo4> riskList = new ArrayList();

                for (LinkedList list : data) {
                    RiskAssetVo4 riskAssetVo4 = new RiskAssetVo4();
                    riskAssetVo4.setSecurityDomain((String) list.get(0));
                    riskAssetVo4.setAssetName((String) list.get(1));
                    riskAssetVo4.setRiskRating((String) list.get(2));
                    riskAssetVo4.setLatestExceptionTime((String) list.get(3));
                    riskList.add(riskAssetVo4);
                }

                listMap.put("riskList", riskList);

                String option1 = null;
                String option2 = null;

                try {
                    option1 = FreemarkerUtil.generateString("RiskAsset01.ftl", "/temp", freeMarkerFields);
                    option2 = FreemarkerUtil.generateString("RiskAsset02.ftl", "/temp", freeMarkerFields);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
                concurrentHashMap.put("RiskAsset", EchartsGenerateUtil.generateEChart(option1));
                concurrentHashMap.put("RiskAssetTop5", EchartsGenerateUtil.generateEChart(option2));
                countDownLatch.countDown();
            }
        }).start();

        //受害者分析
        new Thread(new Runnable() {
            @Override
            public void run() {
                RespVictimVo respVictimVo = (RespVictimVo) ResultMapUtil.map.get("respVictimVo");
                HashMap<String, Object> freeMarkerFields = new HashMap<>();
                freeMarkerFields = new HashMap<>();
                freeMarkerFields.put("xaxis", JSON.toJSONString(respVictimVo.getTopVictim().getXAxis()));
                freeMarkerFields.put("yaxis", JSON.toJSONString(respVictimVo.getTopVictim().getYAxis()));

                ArrayList<ArrayList> data = (ArrayList<ArrayList>) respVictimVo.getLatestVictim().getData();

                ArrayList<VictimVo3> victimList = new ArrayList();

                for (ArrayList list : data) {
                    VictimVo3 victimVo3 = new VictimVo3();
                    victimVo3.setDestGeoCountry((String) list.get(0));
                    victimVo3.setThreatSeverity((String) list.get(1));
                    victimVo3.setSubCategory((String) list.get(2));
                    victimVo3.setStartTime((String) list.get(3));
                    victimList.add(victimVo3);
                }
                listMap.put("victimList", victimList);

                String option3 = null;
                try {
                    option3 = FreemarkerUtil.generateString("Victim.ftl", "/temp", freeMarkerFields);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
                concurrentHashMap.put("VictimTop5", EchartsGenerateUtil.generateEChart(option3));
                countDownLatch.countDown();
            }
        }).start();

        //攻击者分析
        new Thread(new Runnable() {
            @Override
            public void run() {
                RespAttackerVo respAttackerVo = (RespAttackerVo) ResultMapUtil.map.get("respAttackerVo");
                HashMap<String, Object> freeMarkerFields = new HashMap<>();
                freeMarkerFields = new HashMap<>();
                freeMarkerFields.put("xaxis", JSON.toJSONString(respAttackerVo.getTopAttacker().getXAxis()));
                freeMarkerFields.put("yaxis", JSON.toJSONString(respAttackerVo.getTopAttacker().getYAxis()));

                ArrayList<ArrayList> data = (ArrayList<ArrayList>) respAttackerVo.getLatestAttacker().getData();


                ArrayList<AttackerVo4> attackerList = new ArrayList();

                for (ArrayList list : data) {
                    AttackerVo4 attackerVo4 = new AttackerVo4();
                    attackerVo4.setSrcGeoCountry((String) list.get(0));
                    attackerVo4.setKillChain((String) list.get(1));
                    attackerVo4.setThreatSeverity((String) list.get(2));
                    attackerVo4.setStartTime((String) list.get(3));
                    attackerList.add(attackerVo4);
                }
                listMap.put("attackerList", attackerList);

                String option4 = null;
                try {
                    option4 = FreemarkerUtil.generateString("Attacker.ftl", "/temp", freeMarkerFields);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
                concurrentHashMap.put("AttackerTop5", EchartsGenerateUtil.generateEChart(option4));
                countDownLatch.countDown();
            }
        }).start();

        //攻击者TopN分析
        new Thread(new Runnable() {
            @Override
            public void run() {
                RespAttackerTopNVo respAttackerTopNVo = (RespAttackerTopNVo) ResultMapUtil.map.get("respAttackerTopNVo");
                HashMap<String, Object> freeMarkerFields = new HashMap<>();
                freeMarkerFields = new HashMap<>();
                freeMarkerFields.put("titleName", JSON.toJSONString(respAttackerTopNVo.getTopN().get(0).getTitleName()));
                freeMarkerFields.put("data", JSON.toJSONString(respAttackerTopNVo.getTopN().get(0).getData()));
                freeMarkerFields.put("titleName1", JSON.toJSONString(respAttackerTopNVo.getTopN().get(1).getTitleName()));
                freeMarkerFields.put("data1", JSON.toJSONString(respAttackerTopNVo.getTopN().get(1).getData()));
                freeMarkerFields.put("titleName2", JSON.toJSONString(respAttackerTopNVo.getTopN().get(2).getTitleName()));
                freeMarkerFields.put("data2", JSON.toJSONString(respAttackerTopNVo.getTopN().get(2).getData()));
                freeMarkerFields.put("titleName3", JSON.toJSONString(respAttackerTopNVo.getTopN().get(3).getTitleName()));
                freeMarkerFields.put("data3", JSON.toJSONString(respAttackerTopNVo.getTopN().get(3).getData()));
                String option6 = null;
                String option7 = null;
                String option8 = null;
                String option9 = null;
                try {
                    option6 = FreemarkerUtil.generateString("KillChain.ftl", "/temp", freeMarkerFields);
                    option7 = FreemarkerUtil.generateString("DeviceAssetSubType.ftl", "/temp", freeMarkerFields);
                    option8 = FreemarkerUtil.generateString("ModelType.ftl", "/temp", freeMarkerFields);
                    option9 = FreemarkerUtil.generateString("SubCategory.ftl", "/temp", freeMarkerFields);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
                concurrentHashMap.put("KillChainTop5", EchartsGenerateUtil.generateEChart(option6));
                concurrentHashMap.put("DeviceTop5", EchartsGenerateUtil.generateEChart(option7));
                concurrentHashMap.put("ModelTop5", EchartsGenerateUtil.generateEChart(option8));
                concurrentHashMap.put("SubCateGoryTop5", EchartsGenerateUtil.generateEChart(option9));
                countDownLatch.countDown();
            }
        }).start();

        //处置建议
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<SuggestionVo1> suggestionVo1s = (List<SuggestionVo1>) ResultMapUtil.map.get("suggestionVo1s");
                String str = "";
                for (int i = 0; i < suggestionVo1s.size(); i++) {
                    str += suggestionVo1s.get(i).getSuggestion();
                }
                concurrentHashMap.put("Suggestions", IsMatchUtil.isMatch1(str));
                countDownLatch.countDown();
            }
        }).start();


        //当所有子线程都执行完毕后，再继续执行主线程
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        WordPicExportUtil.imageWordExportWeb(listMap, concurrentHashMap, response);

        /*// 获取指定目录下的第一个文件
        File word = new File(WORDPATH);
        String fileName = word.getName(); //下载的文件名

        // 如果文件名不为空，则进行下载
        if (fileName != null) {

            // 如果文件名存在，则进行下载
            if (word.exists()) {
                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(word);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }*/
    }

    /**
     * 没有多线程版
     * @param response
     */
   /* @Override
    public void wordExport(HttpServletResponse response) {

        //图表写入Word模板
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap();

        //攻击时序分布
        RespDTVo respDTVo = distributionTrendService.selectAllByThreatSeverity();
        // 模板参数
        HashMap<String, Object> freeMarkerFields = new HashMap<>();
        freeMarkerFields.put("list", JSON.toJSONString(respDTVo.getList()));
        freeMarkerFields.put("low", JSON.toJSONString(respDTVo.getLow()));
        freeMarkerFields.put("medium", JSON.toJSONString(respDTVo.getMedium()));
        freeMarkerFields.put("high", JSON.toJSONString(respDTVo.getHigh()));
        String option = null;
        try {
            option = FreemarkerUtil.generateString("DistributionTrend.ftl", "/temp", freeMarkerFields);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        concurrentHashMap.put("distributionTrend", EchartsGenerateUtil.generateEChart(option));


        //风险资产
        RespAssetVo riskAsset = riskAssetService.getRiskAsset();
        freeMarkerFields = new HashMap<>();
        freeMarkerFields = new HashMap<>();
        freeMarkerFields.put("list", JSON.toJSONString(riskAsset.getRiskAssetVo2().getList()));
        freeMarkerFields.put("titleName", JSON.toJSONString(riskAsset.getRiskAssetVo3().getTitleName()));
        freeMarkerFields.put("data", JSON.toJSONString(riskAsset.getRiskAssetVo3().getData()));


        String option1 = null;
        String option2 = null;

        try {
            option1 = FreemarkerUtil.generateString("RiskAsset01.ftl", "/temp", freeMarkerFields);
            option2 = FreemarkerUtil.generateString("RiskAsset02.ftl", "/temp", freeMarkerFields);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        concurrentHashMap.put("RiskAsset", EchartsGenerateUtil.generateEChart(option1));
        concurrentHashMap.put("RiskAssetTop5", EchartsGenerateUtil.generateEChart(option2));

        //受害者分析
        RespVictimVo respVictimVo = victimService.selectAllByCountryName();
        freeMarkerFields = new HashMap<>();
        freeMarkerFields.put("xaxis", JSON.toJSONString(respVictimVo.getTopVictim().getXAxis()));
        freeMarkerFields.put("yaxis", JSON.toJSONString(respVictimVo.getTopVictim().getYAxis()));
        String option3 = null;
        try {
            option3 = FreemarkerUtil.generateString("Victim.ftl", "/temp", freeMarkerFields);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        concurrentHashMap.put("VictimTop5", EchartsGenerateUtil.generateEChart(option3));

        //攻击者分析
        RespAttackerVo respAttackerVo = attackerService.selectAllByCountryName();
        freeMarkerFields = new HashMap<>();
        freeMarkerFields.put("xaxis", JSON.toJSONString(respAttackerVo.getTopAttacker().getXAxis()));
        freeMarkerFields.put("yaxis", JSON.toJSONString(respAttackerVo.getTopAttacker().getYAxis()));
        String option4 = null;
        try {
            option4 = FreemarkerUtil.generateString("Attacker.ftl", "/temp", freeMarkerFields);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        concurrentHashMap.put("AttackerTop5", EchartsGenerateUtil.generateEChart(option4));

        //攻击者TopN分析
        RespAttackerTopNVo respAttackerTopNVo = attackerTopService.selectAttackerTopN();
        freeMarkerFields = new HashMap<>();
        freeMarkerFields.put("titleName", JSON.toJSONString(respAttackerTopNVo.getTopN().get(0).getTitleName()));
        freeMarkerFields.put("data", JSON.toJSONString(respAttackerTopNVo.getTopN().get(0).getData()));
        freeMarkerFields.put("titleName1", JSON.toJSONString(respAttackerTopNVo.getTopN().get(1).getTitleName()));
        freeMarkerFields.put("data1", JSON.toJSONString(respAttackerTopNVo.getTopN().get(1).getData()));
        freeMarkerFields.put("titleName2", JSON.toJSONString(respAttackerTopNVo.getTopN().get(2).getTitleName()));
        freeMarkerFields.put("data2", JSON.toJSONString(respAttackerTopNVo.getTopN().get(2).getData()));
        freeMarkerFields.put("titleName3", JSON.toJSONString(respAttackerTopNVo.getTopN().get(3).getTitleName()));
        freeMarkerFields.put("data3", JSON.toJSONString(respAttackerTopNVo.getTopN().get(3).getData()));
        String option6 = null;
        String option7 = null;
        String option8 = null;
        String option9 = null;
        try {
            option6 = FreemarkerUtil.generateString("KillChain.ftl", "/temp", freeMarkerFields);
            option7 = FreemarkerUtil.generateString("DeviceAssetSubType.ftl", "/temp", freeMarkerFields);
            option8 = FreemarkerUtil.generateString("ModelType.ftl", "/temp", freeMarkerFields);
            option9 = FreemarkerUtil.generateString("SubCategory.ftl", "/temp", freeMarkerFields);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        concurrentHashMap.put("KillChainTop5", EchartsGenerateUtil.generateEChart(option6));
        concurrentHashMap.put("DeviceTop5", EchartsGenerateUtil.generateEChart(option7));
        concurrentHashMap.put("ModelTop5", EchartsGenerateUtil.generateEChart(option8));
        concurrentHashMap.put("SubCateGoryTop5", EchartsGenerateUtil.generateEChart(option9));

        List<SuggestionVo1> suggestionVo1s = suggestionService.selectSuggestion().getSuggestionVo1s();
        String str = "";
        for (int i = 0; i < suggestionVo1s.size(); i++) {
            str += suggestionVo1s.get(i).getSuggestion();
        }
        concurrentHashMap.put("Suggestions", IsMatchUtil.isMatch1(str));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WordPicExportUtil.imageWordExportWeb(concurrentHashMap, response);

     *//*   // 获取指定目录下的第一个文件
        File word = new File(WORDPATH);
        String fileName = word.getName(); //下载的文件名

        // 如果文件名不为空，则进行下载
        if (fileName != null) {

            // 如果文件名存在，则进行下载
            if (word.exists()) {
                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(word);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }*//*
    }*/
}
