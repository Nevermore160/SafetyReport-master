package com.lt.service.impl;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.RespAssetVo;
import com.lt.entity.vo.asset.*;
import com.lt.mapper.RiskAssetsMapper;
import com.lt.service.RiskAssetService;
import com.lt.utils.ResultMapUtil;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 19:07
 */
@Service
public class RiskAssetServiceImpl implements RiskAssetService {

    @Autowired
    private RiskAssetsMapper riskAssetsMapper;

    @Override
    public RespAssetVo getRiskAsset(ReqVo reqVo) {
        RespAssetVo respAssetVo = new RespAssetVo();

        CountDownLatch countDownLatch = new CountDownLatch(3);

        //获得安全域、资产名称、风险评价、时间
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RiskAssetVo4> list = riskAssetsMapper.getLatestExceptionTime(reqVo);
                RiskAssetVo1 riskAssetVo1 = new RiskAssetVo1();

                List<List<String>> list1 = new LinkedList<>();

                LinkedList<String> list2;

                for (RiskAssetVo4 riskAssetVo4 : list) {
                    list2 = new LinkedList<>();
                    list2.add(riskAssetVo4.getSecurityDomain());
                    list2.add(riskAssetVo4.getAssetName());
                    list2.add(riskAssetVo4.getRiskRating());
                    list2.add(riskAssetVo4.getLatestExceptionTime());
                    list1.add(list2);
                }
                riskAssetVo1.setData(list1);

                respAssetVo.setRiskAssetVo1(riskAssetVo1);
                countDownLatch.countDown();
            }
        }).start();

        //...........
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RiskAssetVo5> riskRatingNum = riskAssetsMapper.getRiskRatingNum(reqVo);
                RiskAssetVo2<RiskAssetVo5> riskAssetVo2 = new RiskAssetVo2<>();

                riskAssetVo2.setList(riskRatingNum);

                respAssetVo.setRiskAssetVo2(riskAssetVo2);
                countDownLatch.countDown();
            }
        }).start();

        //............
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RiskAssetVo6> securityDomainNum = riskAssetsMapper.getSecurityDomainNum(reqVo);
                RiskAssetVo3 riskAssetVo3 = new RiskAssetVo3();
                List<RiskAssetVo7> list3 = new ArrayList<>();
                List<String> list4 = new LinkedList<>();
                Map<String, Integer> map1 = new HashMap<>();
                map1.put("低风险", 1);
                map1.put("高风险", 2);
                map1.put("已失陷", 3);

                for (RiskAssetVo6 riskAssetVo6 : securityDomainNum) {
                    if (list4.contains(riskAssetVo6.getName()))
                        list3.get(list4.indexOf(riskAssetVo6.getName())).setSum(list3.get(list4.indexOf(riskAssetVo6.getName())).getSum() + map1.get(riskAssetVo6.getRiskRating()));
                    else {
                        list4.add(riskAssetVo6.getName());
                        RiskAssetVo7 riskAssetVo7 = new RiskAssetVo7();
                        riskAssetVo7.setSum(map1.get(riskAssetVo6.getRiskRating()));
                        riskAssetVo7.setName(riskAssetVo6.getName());
                        list3.add(riskAssetVo7);
                    }
                }
                Collections.sort(list3);
                int num;
                if (list3.size() >= 5) num = 5;
                else num = list3.size();
                int[] a = new int[num];
                String[] str = new String[num];
                for (int i = list3.size() - 1; i >= list3.size() - num; i--) {
                    a[list3.size() - 1 - i] = list3.get(i).getSum();
                    str[list3.size() - 1 - i] = list3.get(i).getName();
                }
                riskAssetVo3.setData(a);
                riskAssetVo3.setTitleName(str);
                respAssetVo.setRiskAssetVo3(riskAssetVo3);
                countDownLatch.countDown();
            }
        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ResultMapUtil.method("respAssetVo",respAssetVo);
        return respAssetVo;
    }
}
