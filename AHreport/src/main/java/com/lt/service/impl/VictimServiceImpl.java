package com.lt.service.impl;

import com.lt.entity.vo.*;
import com.lt.entity.vo.victim.*;
import com.lt.mapper.VictimMapper;
import com.lt.service.VictimService;
import com.lt.utils.EnumUtil;
import com.lt.utils.ResultMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/17 17:31
 */
@Service
public class VictimServiceImpl implements VictimService {

    public static final Integer Low = 5;
    public static final Integer Medium = 7;
    public static final Integer High = 9;

    @Autowired
    private VictimMapper victimMapper;

    @Override
    public RespVictimVo selectAllByCountryName(ReqVo reqVo) {
        RespVictimVo respVictimVo = new RespVictimVo();
        CountDownLatch countDownLatch = new CountDownLatch(4);

        List<VictimVo> victimVos = new ArrayList<>();
        List<VictimCategoryVo> victimVo2List = new ArrayList<>();


        //区域和热度关联-----------------------------
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<VictimVo1> victimVo1s = victimMapper.selectCountryHeat(reqVo);
                Map<String, String> stringStringMap = null;
                try {
                    stringStringMap = EnumUtil.countryToCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //HashMap 存国家和热度
                HashMap<String, Integer> countryAndHeat = new HashMap<>();

                for (VictimVo1 victimVo1 : victimVo1s) {
                    boolean flag = countryAndHeat.containsKey(victimVo1.getDestGeoCountry());
                    int sum;

                    //说明HashMap里有这个国家
                    if (flag) {
                        sum = countryAndHeat.get(victimVo1.getDestGeoCountry());
                        if (victimVo1.getThreatSeverity().equals("Low")) {
                            countryAndHeat.put(victimVo1.getDestGeoCountry(), sum + victimVo1.getSum() * Low);
                        } else if (victimVo1.getThreatSeverity().equals("Medium")) {
                            countryAndHeat.put(victimVo1.getDestGeoCountry(), sum + victimVo1.getSum() * Medium);
                        } else if (victimVo1.getThreatSeverity().equals("High")) {
                            countryAndHeat.put(victimVo1.getDestGeoCountry(), sum + victimVo1.getSum() * High);
                        }
                    } else {
                        if (victimVo1.getThreatSeverity().equals("Low")) {
                            countryAndHeat.put(victimVo1.getDestGeoCountry(), victimVo1.getSum() * Low);
                        } else if (victimVo1.getThreatSeverity().equals("Medium")) {
                            countryAndHeat.put(victimVo1.getDestGeoCountry(), victimVo1.getSum() * Low);
                        } else if (victimVo1.getThreatSeverity().equals("High")) {
                            countryAndHeat.put(victimVo1.getDestGeoCountry(), victimVo1.getSum() * High);
                        }
                    }
                }

                VictimVo victimVo;

                for (Map.Entry<String, Integer> entry : countryAndHeat.entrySet()) {
                    victimVo = new VictimVo();
                    victimVo.setName(stringStringMap.get(entry.getKey()));
                    victimVo.setValue(entry.getValue());
                    //把一个国家热度存入List
                    victimVos.add(victimVo);
                }

                countDownLatch.countDown();
            }
        }).start();


        //区域和攻击类型关联-----------------------------
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<VictimVo2> victimVo2s = victimMapper.selectCountryCategory(reqVo);

                Map<String, String> stringStringMap = null;
                try {
                    stringStringMap = EnumUtil.countryToCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //HashMap 存国家和报警子类型
                HashMap<String, String> countryAndCategory = new HashMap<>();

                for (VictimVo2 victimVo2 : victimVo2s) {
                    boolean flag = countryAndCategory.containsKey(victimVo2.getDestGeoCountry());

                    //国家和报警子类型都是排好序的，只存第一个即可
                    if (!flag) {
                        countryAndCategory.put(victimVo2.getDestGeoCountry(), victimVo2.getSubCategory());
                    } else {
                        continue;
                    }
                }


                VictimCategoryVo victimVo2;

                for (Map.Entry<String, String> entry : countryAndCategory.entrySet()) {
                    victimVo2 = new VictimCategoryVo();
                    victimVo2.setName(stringStringMap.get(entry.getKey()));
                    victimVo2.setValue(entry.getValue());
                    //把一个国家热度存入List
                    victimVo2List.add(victimVo2);
                }

                countDownLatch.countDown();
            }
        }).start();

        //受害者前五的记录-------------------------------
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<VictimVo5> victimVo5s = victimMapper.selectCountryCategorySum(reqVo);
                VictimVo6 victimVo6 = new VictimVo6();

                for (int i = 0; i < victimVo5s.size(); i++) {
                    victimVo6.getXAxis()[i] = victimVo5s.get(i).getDestGeoCountry();
                    victimVo6.getYAxis()[i] = victimVo5s.get(i).getSum();
                }

                respVictimVo.setTopVictim(victimVo6);
                countDownLatch.countDown();
            }
        }).start();

        //最新的前十条记录-------------------------------
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<VictimVo3> victimVo3s = victimMapper.selectLatestCountry(reqVo);

                VictimVo4 victimVo4 = new VictimVo4();
                for (VictimVo3 victimVo3 : victimVo3s) {
                    List list = new ArrayList();
                    list.add(victimVo3.getDestGeoCountry());
                    list.add(victimVo3.getThreatSeverity());
                    list.add(victimVo3.getSubCategory());
                    list.add(victimVo3.getStartTime());
                    victimVo4.getData().add(list);
                }

                respVictimVo.setLatestVictim(victimVo4);
                countDownLatch.countDown();
            }
        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        respVictimVo.getMaps().add(victimVos);
        respVictimVo.getMaps().add(victimVo2List);

        ResultMapUtil.method("respVictimVo",respVictimVo);

        return respVictimVo;
    }
}
