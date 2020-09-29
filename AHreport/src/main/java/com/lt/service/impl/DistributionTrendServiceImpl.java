package com.lt.service.impl;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.dt.DTVo;
import com.lt.entity.vo.RespDTVo;
import com.lt.mapper.DistributionTrendMapper;
import com.lt.service.DistributionTrendService;
import com.lt.utils.ResultMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/16 17:13
 */
@Service
public class DistributionTrendServiceImpl implements DistributionTrendService {
    @Autowired
    private DistributionTrendMapper distributionTrendMapper;

    /**
     * 多线程版
     * @return
     */
    @Override
    public RespDTVo selectAllByThreatSeverity(ReqVo reqVo) {

        RespDTVo respDTVo = new RespDTVo();
        CountDownLatch countDownLatch = new CountDownLatch(3);

        //查出所有时间节点
        List<String> timeVos = distributionTrendMapper.selectAllTimeNode(reqVo);

        //低风险
        new Thread(new Runnable() {
            @Override
            public void run() {
                //查出高、中、低三个等级的攻击次数随时间的变化
                List<DTVo> low = distributionTrendMapper.selectAllByThreatSeverity("Low", reqVo);

                int lowArray[] = new int[timeVos.size()];
                for (DTVo dtVo : low) {
                    int l = timeVos.indexOf(dtVo.getStartTime());
                    lowArray[l] = dtVo.getCountTime();
                }

                respDTVo.setLow(lowArray);
                countDownLatch.countDown();
            }
        }).start();

        //中风险
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<DTVo> medium = distributionTrendMapper.selectAllByThreatSeverity("Medium", reqVo);

                int mediumArray[] = new int[timeVos.size()];


                for (DTVo dtVo : medium) {
                    int m = timeVos.indexOf(dtVo.getStartTime());
                    mediumArray[m] = dtVo.getCountTime();
                }

                respDTVo.setMedium(mediumArray);
                countDownLatch.countDown();

            }
        }).start();

        //高风险
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<DTVo> high = distributionTrendMapper.selectAllByThreatSeverity("High", reqVo);

                int highArray[] = new int[timeVos.size()];

                for (DTVo dtVo : high) {
                    int h = timeVos.indexOf(dtVo.getStartTime());
                    highArray[h] = dtVo.getCountTime();
                }
                respDTVo.setHigh(highArray);
                countDownLatch.countDown();
            }
        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        respDTVo.setList(timeVos);
        ResultMapUtil.method("respDTVo",respDTVo);
        return respDTVo;
    }
}
