package com.lt.controller;

import com.lt.entity.vo.ReqVo;
import com.lt.entity.vo.RespAttackerVo;
import com.lt.entity.vo.RespDTVo;
import com.lt.service.DistributionTrendService;
import com.lt.utils.DataResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 时间与趋势Controller
 *
 * @author teng.li
 * @version 1.0
 * @date 2020/8/16 16:01
 */
@RestController
@RequestMapping("/DistributionTrend")
@CrossOrigin
public class DTController {
    @Autowired
    private DistributionTrendService distributionTrendService;

    /**
     * 返回高、中、低攻击强度随时间的变化
     *
     * @return
     */
    @PostMapping("/all")
    public DataResultUtil<RespDTVo> DistributionTrend(@RequestBody ReqVo reqVo) {
        if (reqVo.getEndTime() == null || reqVo.getStartTime() == null)
            return new DataResultUtil<>(200, "响应成功", distributionTrendService.selectAllByThreatSeverity(reqVo));

        if (reqVo.getStartTime().equals("") || reqVo.getEndTime().equals(""))
            return new DataResultUtil<>(5002,"传入参数不能为空",new RespDTVo());

        RespDTVo respDTVo = distributionTrendService.selectAllByThreatSeverity(reqVo);
        DataResultUtil<RespDTVo> result = new DataResultUtil<>(200, "响应成功", respDTVo);
        return result;
    }

}
