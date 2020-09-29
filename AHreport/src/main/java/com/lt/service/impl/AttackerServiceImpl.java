package com.lt.service.impl;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.RespAttackerVo;
import com.lt.entity.vo.attacker.*;
import com.lt.mapper.AttackerMapper;
import com.lt.service.AttackerService;
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
 * @date 2020/8/18 16:49
 */
@Service
public class AttackerServiceImpl implements AttackerService {

    public static final Integer Low = 5;
    public static final Integer Medium = 7;
    public static final Integer High = 9;

    @Autowired
    private AttackerMapper attackerMapper;

    @Override
    public RespAttackerVo selectAllByCountryName(ReqVo reqVo) {
        RespAttackerVo respAttackerVo = new RespAttackerVo();

        CountDownLatch countDownLatch = new CountDownLatch(4);

        //区域和热度关联
        List<AttackerVo> attackerVos = new ArrayList<>();
        //区域和工具类关联
        List<AttackerVo7> attackerVo7s = new ArrayList<>();

        //区域和热度关联-----------------------------
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AttackerVo1> attackerVo1s = attackerMapper.selectCountrySumSeverity(reqVo);

                Map<String, String> stringStringMap = null;
                try {
                    stringStringMap = EnumUtil.countryToCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //HashMap 存国家和热度
                HashMap<String, Integer> countryAndHeat = new HashMap<>();

                for (AttackerVo1 attackerVo1 : attackerVo1s) {
                    boolean flag = countryAndHeat.containsKey(attackerVo1.getSrcGeoCountry());
                    int sum;

                    //说明HashMap里有这个国家
                    if (flag) {
                        sum = countryAndHeat.get(attackerVo1.getSrcGeoCountry());
                        if (attackerVo1.getThreatSeverity().equals("Low")) {
                            countryAndHeat.put(attackerVo1.getSrcGeoCountry(), sum + attackerVo1.getSum() * Low);
                        } else if (attackerVo1.getThreatSeverity().equals("Medium")) {
                            countryAndHeat.put(attackerVo1.getSrcGeoCountry(), sum + attackerVo1.getSum() * Medium);
                        } else if (attackerVo1.getThreatSeverity().equals("High")) {
                            countryAndHeat.put(attackerVo1.getSrcGeoCountry(), sum + attackerVo1.getSum() * High);
                        }
                    } else {
                        if (attackerVo1.getThreatSeverity().equals("Low")) {
                            countryAndHeat.put(attackerVo1.getSrcGeoCountry(), attackerVo1.getSum() * Low);
                        } else if (attackerVo1.getThreatSeverity().equals("Medium")) {
                            countryAndHeat.put(attackerVo1.getSrcGeoCountry(), attackerVo1.getSum() * Low);
                        } else if (attackerVo1.getThreatSeverity().equals("High")) {
                            countryAndHeat.put(attackerVo1.getSrcGeoCountry(), attackerVo1.getSum() * High);
                        }
                    }
                }

                AttackerVo attackerVo;

                for (Map.Entry<String, Integer> entry : countryAndHeat.entrySet()) {
                    attackerVo = new AttackerVo();
                    attackerVo.setName(stringStringMap.get(entry.getKey()));
                    attackerVo.setValue(entry.getValue());
                    //把一个国家热度存入List
                    attackerVos.add(attackerVo);
                }

                countDownLatch.countDown();
            }
        }).start();

        //区域和攻击链关联-----------------------------
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AttackerVo2> attackerVo2s = attackerMapper.selectCountryKillChain(reqVo);

                //HashMap 存国家和攻击链
                HashMap<String, String> countryAndKillChain = new HashMap<>();

                for (AttackerVo2 attackerVo2 : attackerVo2s) {
                    boolean flag = countryAndKillChain.containsKey(attackerVo2.getSrcGeoCountry());

                    //国家和攻击类型都是排好序的，只存第一个即可
                    if (!flag) {
                        countryAndKillChain.put(attackerVo2.getSrcGeoCountry(), attackerVo2.getKillChain());
                    } else {
                        continue;
                    }
                }

                Map<String, String> stringStringMap = null;
                try {
                    stringStringMap = EnumUtil.countryToCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                AttackerVo7 attackerVo7;

                for (Map.Entry<String, String> entry : countryAndKillChain.entrySet()) {
                    attackerVo7 = new AttackerVo7();
                    attackerVo7.setName(stringStringMap.get(entry.getKey()));
                    attackerVo7.setValue(entry.getValue());
                    //把一个国家热度存入List
                    attackerVo7s.add(attackerVo7);
                }


                countDownLatch.countDown();
            }
        }).start();

        respAttackerVo.getMaps().add(attackerVos);
        respAttackerVo.getMaps().add(attackerVo7s);

        //攻击者前五的记录-------------------------------
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AttackerVo3> attackerVo3s = attackerMapper.selectCountryKillChainSum(reqVo);
                AttackerVo5 attackerVo5 = new AttackerVo5();

                for (int i = 0; i < attackerVo3s.size(); i++) {
                    attackerVo5.getXAxis()[i] = attackerVo3s.get(i).getSrcGeoCountry();
                    attackerVo5.getYAxis()[i] = attackerVo3s.get(i).getSum();
                }

                respAttackerVo.setTopAttacker(attackerVo5);
                countDownLatch.countDown();
            }
        }).start();

        //最新的前十条记录-------------------------------
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AttackerVo4> attackerVo4s = attackerMapper.selectLatestCountry(reqVo);

                AttackerVo6 attackerVo6 = new AttackerVo6();
                Map<String, String> killChainMap = null;
                try {
                    killChainMap = EnumUtil.killChainToChar();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (AttackerVo4 attackerVo4 : attackerVo4s) {
                    List list = new ArrayList();
                    list.add(attackerVo4.getSrcGeoCountry());
                    list.add(killChainMap.get(attackerVo4.getKillChain()));
                    list.add(attackerVo4.getThreatSeverity());
                    list.add(attackerVo4.getStartTime());
                    attackerVo6.getData().add(list);
                }

                respAttackerVo.setLatestAttacker(attackerVo6);
                countDownLatch.countDown();
            }
        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ResultMapUtil.method("respAttackerVo", respAttackerVo);
        return respAttackerVo;
    }
}
