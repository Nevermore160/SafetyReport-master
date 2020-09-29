package com.lt.controller;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.RespAssetVo;
import com.lt.entity.vo.RespDTVo;
import com.lt.service.RiskAssetService;
import com.lt.utils.DataResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/19 19:50
 */
@RestController
@RequestMapping("/RiskAsset")
@CrossOrigin
public class RiskAssetController {
    @Autowired
    private RiskAssetService riskAssetService;

    /**
     * 返回高、中、低攻击强度随时间的变化
     *
     * @return
     */
    @PostMapping("/all")
    public DataResultUtil<RespAssetVo> DistributionTrend(@RequestBody ReqVo reqVo) {

        if (reqVo.getEndTime() == null || reqVo.getStartTime() == null)
            return new DataResultUtil<>(200, "响应成功", riskAssetService.getRiskAsset(reqVo));

        if (reqVo.getStartTime().equals("") || reqVo.getEndTime().equals(""))
            return new DataResultUtil<>(5002,"传入参数不能为空",new RespAssetVo());

        RespAssetVo riskAsset = riskAssetService.getRiskAsset(reqVo);
        DataResultUtil<RespAssetVo> result = new DataResultUtil<>(200, "响应成功", riskAsset);
        return result;
    }
}
