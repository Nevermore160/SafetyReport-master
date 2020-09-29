package com.lt.service.impl;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.RespAttackerTopNVo;
import com.lt.entity.vo.top.TopVo1;
import com.lt.entity.vo.top.TopVo2;
import com.lt.mapper.AttackerTopMapper;
import com.lt.service.AttackerTopService;
import com.lt.utils.EnumUtil;
import com.lt.utils.ResultMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 12:49
 */
@Service
public class AttackerTopServiceImpl implements AttackerTopService {

    @Autowired
    private AttackerTopMapper attackerTopMapper;

    @Override
    public RespAttackerTopNVo selectAttackerTopN(ReqVo reqVo) {
        RespAttackerTopNVo respAttackerTopNVo = new RespAttackerTopNVo();
        List<TopVo1> topN = new ArrayList<>();
        final CountDownLatch countDownLatch = new CountDownLatch(4);

        TopVo1 topVoKillChill = new TopVo1();
        TopVo1 topVoDeviceAST = new TopVo1();
        TopVo1 topVoModelType = new TopVo1();
        TopVo1 topVoSubCategory = new TopVo1();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TopVo2> topVo2s = attackerTopMapper.selectTopByKillChain(reqVo);
                Map<String, String> stringStringMap = null;
                try {
                    stringStringMap = EnumUtil.killChainToChar();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < topVo2s.size(); i++) {
                    topVoKillChill.getData()[i] = topVo2s.get(i).getData();
                    topVoKillChill.getTitleName()[i] = stringStringMap.get(topVo2s.get(i).getTitleName());
                }
                countDownLatch.countDown();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TopVo2> topVo2s1 = attackerTopMapper.selectTopByDeviceAST(reqVo);
                for (int i = 0; i < topVo2s1.size(); i++) {
                    topVoDeviceAST.getData()[i] = topVo2s1.get(i).getData();
                    topVoDeviceAST.getTitleName()[i] = topVo2s1.get(i).getTitleName();
                }
                countDownLatch.countDown();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TopVo2> topVo2s2 = attackerTopMapper.selectTopByModelType(reqVo);
                for (int i = 0; i < topVo2s2.size(); i++) {
                    topVoModelType.getData()[i] = topVo2s2.get(i).getData();
                    topVoModelType.getTitleName()[i] = topVo2s2.get(i).getTitleName();
                }
                countDownLatch.countDown();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TopVo2> topVo2s3 = attackerTopMapper.selectTopBySubCategory(reqVo);
                for (int i = 0; i < topVo2s3.size(); i++) {
                    topVoSubCategory.getData()[i] = topVo2s3.get(i).getData();
                    topVoSubCategory.getTitleName()[i] = topVo2s3.get(i).getTitleName();
                }
                countDownLatch.countDown();
            }
        }).start();

        //当所有子线程都执行完毕后，再继续执行主线程
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        respAttackerTopNVo.setTopN(topN);
        ResultMapUtil.method("respAttackerTopNVo", respAttackerTopNVo);

        topN.add(topVoKillChill);
        topN.add(topVoDeviceAST);
        topN.add(topVoModelType);
        topN.add(topVoSubCategory);

        return respAttackerTopNVo;
    }

    /*@Override
    public RespAttackerTopNVo selectAttackerTopN() {
        RespAttackerTopNVo respAttackerTopNVo = new RespAttackerTopNVo();
        List<TopVo1> topN = new ArrayList<>();
        final CountDownLatch countDownLatch = new CountDownLatch(4);

        List<TopVo2> topVo2s = attackerTopMapper.selectTopByKillChain();
        List<TopVo2> topVo2s1 = attackerTopMapper.selectTopByDeviceAST();
        List<TopVo2> topVo2s2 = attackerTopMapper.selectTopByModelType();
        List<TopVo2> topVo2s3 = attackerTopMapper.selectTopBySubCategory();


        TopVo1 topVoKillChill = new TopVo1();
        TopVo1 topVoDeviceAST = new TopVo1();
        TopVo1 topVoModelType = new TopVo1();
        TopVo1 topVoSubCategory = new TopVo1();

        for (int i = 0; i < topVo2s.size(); i++) {
            topVoKillChill.getData()[i] = topVo2s.get(i).getData();
            topVoKillChill.getTitleName()[i] = topVo2s.get(i).getTitleName();
        }

        for (int i = 0; i < topVo2s1.size(); i++) {
            topVoDeviceAST.getData()[i] = topVo2s1.get(i).getData();
            topVoDeviceAST.getTitleName()[i] = topVo2s1.get(i).getTitleName();
        }

        for (int i = 0; i < topVo2s2.size(); i++) {
            topVoModelType.getData()[i] = topVo2s2.get(i).getData();
            topVoModelType.getTitleName()[i] = topVo2s2.get(i).getTitleName();
        }

        for (int i = 0; i < topVo2s3.size(); i++) {
            topVoSubCategory.getData()[i] = topVo2s3.get(i).getData();
            topVoSubCategory.getTitleName()[i] = topVo2s3.get(i).getTitleName();
        }

        topN.add(topVoKillChill);
        topN.add(topVoDeviceAST);
        topN.add(topVoModelType);
        topN.add(topVoSubCategory);

        respAttackerTopNVo.setTopN(topN);

        return respAttackerTopNVo;
    }*/
}
